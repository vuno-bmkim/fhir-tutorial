import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/*")
public class Example06_SimpleRestfulServer extends RestfulServer {
    @Override
    protected void initialize() throws ServletException {
        // Context 설정
        setFhirContext(FhirContext.forDstu3());

        // Resource Provider 등록
        registerProvider(new Example05_ResourceProviders());

    }
}
