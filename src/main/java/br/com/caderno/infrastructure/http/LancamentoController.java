package br.com.caderno.infrastructure.http;

import br.com.caderno.application.ConsultarCadernoService;
import br.com.caderno.application.LancamentoOutput;
import br.com.caderno.application.RegistrarLancamentoService;
import br.com.caderno.application.ResumoMensalOutput;
import br.com.caderno.domain.Categoria;
import jakarta.validation.Valid;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
    private final RegistrarLancamentoService registro;
    private final ConsultarCadernoService consulta;
    private final TranscriptionModel transcritor;
    private final ChatClient conversa;
    private final TextToSpeechModel narrador;

    public LancamentoController(
            RegistrarLancamentoService registro,
            ConsultarCadernoService consulta,
            TranscriptionModel transcritor,
            @Value("classpath:prompts/caderno.st") Resource prompt,
            ChatClient.Builder chatClientBuilder,
            TextToSpeechModel narrador
    ) throws IOException {
        this.registro = registro;
        this.consulta = consulta;
        this.transcritor = transcritor;
        this.conversa = chatClientBuilder
                .defaultSystem(prompt.getContentAsString(StandardCharsets.UTF_8))
                .defaultTools(registro, consulta)
                .build();
        this.narrador = narrador;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoOutput criar(@Valid @RequestBody CriarLancamentoRequest request) {
        return registro.executar(request.paraInput());
    }

    @GetMapping
    public List<LancamentoOutput> listar(@RequestParam Categoria categoria) {
        return consulta.porCategoria(categoria);
    }

    @GetMapping("/resumo")
    public ResumoMensalOutput resumo(@RequestParam String mes) {
        return consulta.fecharMes(mes);
    }

    @PostMapping(value = "/conversa", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mpeg")
    public ResponseEntity<Resource> conversar(@RequestParam("audio") MultipartFile audio) {
        String texto = transcritor.transcribe(audio.getResource());
        String resposta = conversa.prompt().user(texto).call().content();
        ByteArrayResource mp3 = new ByteArrayResource(narrador.call(resposta));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("resposta-caderno.mp3").build().toString())
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(mp3);
    }
}
