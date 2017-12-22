function findRoofById(id) {
	for(var i=0;i < roofList.length; i++) {
		if(id === roofList[i].roofId) {
			return roofList[i];
		}
	}
};
