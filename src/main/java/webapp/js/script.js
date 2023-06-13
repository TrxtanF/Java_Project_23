
 //open popup
 function openPopup() {
      var popup = document.getElementById("add-student-popup");
      popup.style.display = "block";
    }



      // close popup
 function closePopup() {
    var popup = document.getElementById("add-student-popup");
    popup.style.display = "none";
    }


 function updateSliderValue(value) {
    var sliderValue = document.getElementById("slider-value");
    sliderValue.textContent = value;
    }

 function submitForm() {
   var name = document.getElementById("name").value;
   var kurs = document.getElementById("kurs").value;
   var value = document.getElementById("slider").value;

   var data = {
     name: name,
     kurs: kurs,
     value: value
   };

   fetch('/api/endpoint', {
     method: 'POST',
     headers: {
       'Content-Type': 'application/json'
     },
     body: JSON.stringify(data)
   })
     .then(response => response.json())
     .then(result => {
       // backend response if needed
       console.log(result);
     })
     .catch(error => {
       // errors control
       console.error(error);
     });
 }



