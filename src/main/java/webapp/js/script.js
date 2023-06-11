
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
     }


