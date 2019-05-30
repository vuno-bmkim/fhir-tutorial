function getPatientName (pt) {
    if (pt.name) {
      var names = pt.name.map(function(name) {
        return name.given.join(" ") + " " + name.family.join(" ");
      });
      return names.join(" / ")
    } else {
      return "anonymous";
    }
  }
  
  function getMedicationName (medCodings) {
    var coding = medCodings.find(function(c){
      return c.system == "http://www.nlm.nih.gov/research/umls/rxnorm";
    });
  
    return coding && coding.display || "Unnamed Medication(TM)"
  }
  
  function displayPatient (pt) {
    document.getElementById('patient_name').innerHTML = getPatientName(pt);
  }
  
  var med_list = document.getElementById('med_list');
  
  function displayMedication (medCodings) {
    med_list.innerHTML += "<li> " + getMedicationName(medCodings) + "</li>";
  }
  
  // Create a FHIR client (server URL, patient id in `demo`)
  var smart = FHIR.client(demo);
  var pt = smart.patient;
  console.log(smart);
  //console.log(pt);
  // Create a patient banner by fetching + rendering demographics
  smart.patient.read().then(function(pt) {
    //console.log(pt);
    displayPatient (pt);
  });
  
  // A more advanced query: search for active Prescriptions, including med details
  smart.patient.api.fetchAllWithReferences({type: "MedicationOrder"},["MedicationOrder.medicationReference"]).then(function(results, refs) {
     results.forEach(function(prescription){
          if (prescription.medicationCodeableConcept) {
              displayMedication(prescription.medicationCodeableConcept.coding);
          } else if (prescription.medicationReference) {
              var med = refs(prescription, prescription.medicationReference);
              displayMedication(med && med.code.coding || []);
          }
     });
  });



  smart.api.read({type: 'MedicationOrder', id: 'smart-MedicationOrder-127'}).done(function(results){
    console.log('read');
    console.log(results);
  })

  smart.api.vread({type: 'MedicationOrder', id: 'smart-MedicationOrder-127', versionId: 1}).done(function(results){
    console.log('vread');
    console.log(results);
  })

  smart.api.search({type: 'MedicationOrder'}).done(function(results){
    console.log('search');
    console.log(results);
  })

  smart.patient.api.search({type: 'MedicationOrder'}).done(function(results){
    console.log('patient search');
    console.log(results);
  })
