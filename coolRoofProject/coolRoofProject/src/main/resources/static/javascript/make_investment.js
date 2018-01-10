function updateFields_and_Check_Area() {
   
   var selectedRoofs = "";
   var comma = ",";
   var semicolon = ";"
   var investmentRoofCount = 0;
	
   var selectedRegion = document.getElementById("avaliable-region");
   var selectedValueRegion = selectedRegion.options[selectedRegion.selectedIndex].value;
   
   var selectedValueSpace = document.getElementById("selectedSpace").value;
   //alert(selectedValueSpace);
   
   //clears drop down menue
   clearDropDownMenue(document.getElementById("selectedInvestment"));
   
   if(selectedValueSpace <= 0 || isNaN(selectedValueSpace)){
	   
	   $(".make-investment-fields-display-none").attr('class', 'make-investment-fields-display');
	   document.getElementById("field-error").innerHTML = "Incorrect input. Please select positive Numbers";
	   document.getElementById("confirm-button").style.display = "none";
//	   alert("Incorrect input. Please select positive Numbers");
	   return;
   }
   
   //String representation roofId,space for taken roof
   for(var i=0; i < roofList.length; i++ ){
	   //check if region is equal and if roof has some space left
	   if(roofList[i].region === selectedValueRegion && roofList[i].areaLeft > 0) {
		   //in this case we only take a part of a roof and there will be some area left
		   if(selectedValueSpace - roofList[i].areaLeft <= 0){
			  
			  var temp = roofList[i].areaLeft;
			  roofList[i].areaLeft = roofList[i].areaLeft - selectedValueSpace;			  
			 
			  //build the string representation (roofID, space; ...)
			  selectedRoofs = selectedRoofs.concat(roofList[i].roofId);		
			  selectedRoofList[investmentRoofCount++] = roofList[i]; 
			  //splitting between values
			  selectedRoofs = selectedRoofs.concat(comma);									
			  //the area we use for this investment
			  selectedRoofs = selectedRoofs.concat(selectedValueSpace);
			  //splitting between roofs
			  selectedRoofs = selectedRoofs.concat(semicolon); 								
			  
			  //we covered all the selected space
			  selectedValueSpace = 0;
			  
			  //alert(selectedRoofs);
			  roofList[i].areaLeft = temp;
			  break; // last roof so we only take a part of this roof and break out of the while
		    } else {
		      //use the whole space of the roof
		   	  selectedValueSpace = selectedValueSpace - roofList[i].areaLeft;
		   	  //build the string representation (roofID, space; ...)
			  selectedRoofs = selectedRoofs.concat(roofList[i].roofId);
			  selectedRoofList[investmentRoofCount++] = roofList[i]; 
			  //splitting between values
			  selectedRoofs = selectedRoofs.concat(comma);
			  //the area we use for this investment
			  selectedRoofs = selectedRoofs.concat(roofList[i].areaLeft);
			  //splitting between roofs
			  selectedRoofs = selectedRoofs.concat(semicolon);
		   }  
	   }   
   }
   
   //not enough roofs
   if(selectedValueSpace > 0) {
	   //handle this properly
	   selectedRoofList = [];
	   $(".make-investment-fields-display-none").attr('class', 'make-investment-fields-display');
	   document.getElementById("field-error").innerHTML = "Too less roofs registered for your investment size in this region";
	   document.getElementById("confirm-button").style.display = "none";
	   //alert("Too less roofs registered for your investmentsize in this region");
	   
   }
   else {
	   
	   //set roofIds for BACKEND
	   document.getElementById('roofIds').value = selectedRoofs;
	   //set date for BACKEND
	   document.getElementById('investmentDate').value = new Date();
	   //display confirm button
	   document.getElementById("confirm-button").style.display = "block";
	   //update dropDown
	   updateDropDownMenue(document.getElementById("selectedInvestment"));
	   //init Map
	   initMap();
	   //change of select Button
	   document.getElementById("select-button").innerHTML = 'Change Investment';
	   $(".make-investment-fields-display").attr('class', 'make-investment-fields-display-none');
   }
   
};

//this function finds roof by ID
function findRoofById(id) {
	for(var i=0;i < roofList.length; i++) {
		if(id === roofList[i].roofId) {
			return roofList[i];
		}
	}
};

//this function updates the dropDown menue after each changed investment
function updateDropDownMenue(dropDown) {
	
	for(var i = 0; i < selectedRoofList.length; i++) {
		var option = document.createElement("option");
		option.value = selectedRoofList[i].roofId;
		//TODO: change the name in drop down menue
		option.text = 'Roof ' + i; 
		dropDown.add(option, null);
	}
};

//this function clears the dropDown menue before each changed investment
function clearDropDownMenue(dropDown) {
	
	var i;
    for(i = dropDown.options.length - 1 ; i >= 0 ; i--) {
        dropDown.remove(i);
    }
};

//this function inits the map to show selected investment
function initMap() {
		
		var selectedRoof = document.getElementById("selectedInvestment").value;
		
		//get attributes for selectedMap
		longitude = findRoofById(parseInt(selectedRoof)).longitude;
		latitude = findRoofById(parseInt(selectedRoof)).latitude;
		zoomfactor = findRoofById(parseInt(selectedRoof)).zoomFactor;
		jsonString = findRoofById(parseInt(selectedRoof)).roofPolygon;
		
		map = new google.maps.Map(document.getElementById(mapName), {
	        center: { lat: parseFloat(latitude), lng: parseFloat(longitude) },
	        zoom: parseInt(zoomfactor),
	        //only show roadmap type of map, and disable ability to switch to other type
	        mapTypeId: google.maps.MapTypeId.SATELLITE,
	        mapTypeControl: false,
	        tilt:0, 
	        streetViewControl: false
	    });
		//disable edit functions
		map.data.setStyle({
	        editable: false,
	        draggable: false
	    });
	    
		//loads polygon
	    loadPolygons(jsonString);    
	    
	    //update fields
	    updateFields(document.getElementById("selectedInvestment").value, document.getElementById("selectedSpace").value);
};

function loadPolygons(jsonString) {
	var data = JSON.parse(jsonString); 
	map.data.addGeoJson(data) 
};

//this function updates the fields 
function updateFields(selectedRoof, selectedSpace) {
	
	var tempRoof = findRoofById(parseInt(selectedRoof));
	
	document.getElementById("selectedLongitude").value = tempRoof.longitude;
	document.getElementById("selectedLatitude").value = tempRoof.latitude;
	
	var magnitude = (parseInt(tempRoof.price)) * (parseInt(selectedSpace));
	document.getElementById("selectedMagnitude").value = magnitude
	document.getElementById("selectedAge").value = tempRoof.age;
	
};