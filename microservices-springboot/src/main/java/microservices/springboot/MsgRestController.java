package microservices.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix="msg")
public class MsgRestController {

	private String backendServiceHost;
	private int backendServicePort;
	private String mensagem;
	private RestTemplate template = new RestTemplate();

	@RequestMapping(value = "/msg", method = RequestMethod.GET, produces = "text/plain")
	public String msg() {
		
		String backendServiceUrl = String.format("http://%s:%d/api//backend?msg={mensagem}", backendServiceHost,
													backendServicePort);
		ResponseDTO responseDTO = template.getForObject(backendServiceUrl, ResponseDTO.class, mensagem);
		
		return responseDTO.getMsg() + " - hostname: " + responseDTO.getIp();
	}

	public String getBackendServiceHost() {
		return backendServiceHost;
	}

	public void setBackendServiceHost(String backendServiceHost) {
		this.backendServiceHost = backendServiceHost;
	}

	public int getBackendServicePort() {
		return backendServicePort;
	}

	public void setBackendServicePort(int backendServicePort) {
		this.backendServicePort = backendServicePort;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
	
}
