import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Patient;

import java.util.List;

public class Example05_ResourceProviders implements IResourceProvider {
    public Class<Patient> getResourceType(){
        return Patient.class;
    }

    @Read
    public Patient read(@IdParam IdType theId){
        return null; // populate this
    }

    @Create
    void create(@ResourceParam Patient thePatient){
        //save the resource
    }

    @Search
    List<Patient> search(
            @OperationParam(name="family")StringParam theFamily,
            @OperationParam(name="given")StringParam theGiven
            ){
        return null; // populate this
    }
}
