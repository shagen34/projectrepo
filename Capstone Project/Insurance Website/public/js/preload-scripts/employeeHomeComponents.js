// Script for dynamically building  html components
function showEdit(element, field, type, data){
    console.log("got hre")
    let edit = document.createElement('button');
    edit.innerText = "Edit Field";
    edit.style.float = "right";
    edit.style.margin = "0";
    edit.setAttribute("data-target", "editModal");
    edit.setAttribute("onclick", "showEditBox('" + element.innerText + "','" + field + "','" + type + "','" + JSON.stringify(data) + "')");
    edit.setAttribute("class", "edit");

    
    $(element).hover(function(){
        $(this).addClass("yellow");
        $(this).append(edit);
    }, function(){
        $(this).removeClass("yellow");
        $(".edit").remove();
    });
}

function createUpdate(field, collection){
    var updateCol = "";
    var key, keyField, updateValue;
    if(collection == "app" || collection == "client"){
        updateCol = "clients";
        keyField = "applicationNum";
        let getKey = document.getElementById("key").innerText;
        key = getKey.split(': ')[1];
        if(field == "address"){
            updateValue = [
                document.getElementById("streetField").value,
                document.getElementById("cityField").value,
                document.getElementById("stateField").value,
                document.getElementById("zipField").value
            ]
        }
        else {
            updateValue = document.getElementById("changeField").value;
        }
    }
    else {
        updateCol = "claims";
        keyField = "claimNumber";
        let getKey = document.getElementById("key").innerText;
        key = getKey.split(': ')[1];
        updateValue = document.getElementById("changeField").value;
    }
    updateField(updateCol, key, keyField, field, updateValue);
}

function showEditBox(element, field, type, data){
    let div = document.createElement('div');
    div.setAttribute("class", "modal");
    div.setAttribute("id", "editModal");
    let content = document.createElement('div');
    content.setAttribute("class", "modal-content");
    let prev = document.createElement("p");
    prev.innerText = "Previous Entry for " + element;
    let input = document.createElement("div");
    input.setAttribute("class", "input-field");
    let confirm = document.createElement("button");
    confirm.setAttribute("id", "confirm");
    confirm.innerText = "Confirm";
    confirm.style.display = "inline";
    confirm.style.float = "center";
    confirm.style.marginTop = "0";
    confirm.style.marginBottom = "0";
    confirm.style.marginLeft = "0";
    confirm.style.marginRight = "0";
    confirm.setAttribute("class", "modalBtn");
    let cancel = document.createElement("button");
    cancel.setAttribute("id", "cancel");
    cancel.innerText = "Cancel";
    cancel.style.display = "inline";
    cancel.style.float = "center";
    cancel.style.marginTop = "0";
    cancel.style.marginBottom = "0";
    cancel.style.marginLeft = "0";
    cancel.style.marginRight = "0";
    cancel.setAttribute("class", "modalBtn");

    if(field == "address"){
        let inputEle1 = document.createElement("input");
        inputEle1.setAttribute("id", "streetField");
        let streetLabel = document.createElement("label");
        streetLabel.setAttribute("for", "streetField");
        streetLabel.innerText = "New Street:";
        let inputEle2 = document.createElement("input");
        inputEle2.setAttribute("id", "cityField");
        let cityLabel = document.createElement("label");
        cityLabel.setAttribute("for", "cityField");
        cityLabel.innerText = "New City:";
        let inputEle3 = document.createElement("input");
        inputEle3.setAttribute("id", "stateField");
        let stateLabel = document.createElement("label");
        stateLabel.setAttribute("for", "stateField");
        stateLabel.innerText = "New State:";
        let inputEle4 = document.createElement("input");
        inputEle4.setAttribute("id", "zipField");
        let zipLabel = document.createElement("label");
        zipLabel.setAttribute("for", "zipField");
        zipLabel.innerText = "New Zip:";
        content.appendChild(prev);
        content.appendChild(streetLabel);
        content.appendChild(inputEle1);
        content.appendChild(cityLabel);
        content.appendChild(inputEle2);
        content.appendChild(stateLabel);
        content.appendChild(inputEle3);
        content.appendChild(zipLabel);
        content.appendChild(inputEle4);
        content.appendChild(input);
    }
    else {
        let inputEle = document.createElement("input");
        inputEle.setAttribute("id", "changeField");
        let label = document.createElement("label");
        label.setAttribute("for", "changeField");
        label.innerText = "New Entry:";
        content.appendChild(prev);
        input.appendChild(inputEle);
        content.appendChild(label);
        content.appendChild(input);
    }
    
    content.appendChild(confirm);
    content.appendChild(cancel);
    div.appendChild(content);
    document.getElementById("contentPane").appendChild(div);
    var instance = M.Modal.init(div);
    instance.open();
    var currentData = JSON.parse(data);
    $('#cancel').click(function() {
        instance.close();
    });
    $('#confirm').click(function(){
        createUpdate(field, type);
        instance.close();
        if(type == "app"){
            getApplication(currentData.applicationNum);
        }
        else if(type == "client"){
            getClient(currentData.applicationNum);
        }
        else {
            getClaim(currentData.claimNumber);
        }
    });
}

function displaySearchResults(data){
    //Change polnum
    let polnum = data.newAppNum;
    let title = "Name: " + data.name;
    let addr = "Address: " 
            + data.address + " " 
            + data.city + ", " 
            + data.state + " " 
            + data.zip;
    let poltxt = "Policy Number: " + polnum;

    // create card element
    let holder = document.createElement('div');
    holder.setAttribute("class", "col s12");
    let header = document.createElement('span');
    let headerTxt = document.createTextNode(title);
    header.appendChild(headerTxt);
    let card = document.createElement("div");
    card.setAttribute("class", "card small blue-grey darken-1");
    card.setAttribute("id", "result");
    let stack = document.createElement("div");
    stack.setAttribute("class", "card-stacked");
    let content = document.createElement("div");
    content.setAttribute("class", "card-content white-text");
    let action = document.createElement("div");
    action.setAttribute("class", "card-action");
    let p = document.createElement("p");
    let ptxt = document.createTextNode(addr);
    let p2 = document.createElement("p");
    let p2txt = document.createTextNode(poltxt);

    let viewBtn = document.createElement('btn');
    let btnTxt = document.createTextNode("View Client Details")
    viewBtn.setAttribute("class", "wave-effect waves-light btn");
    viewBtn.addEventListener("click", displayClient.bind(null, data));
    viewBtn.appendChild(btnTxt);

    action.appendChild(viewBtn);
    p2.appendChild(p2txt);
    p.appendChild(ptxt);
    content.appendChild(header);
    content.appendChild(p2);
    content.appendChild(p);
    content.appendChild(action);
    stack.appendChild(content);
    card.appendChild(stack);
    //holder.appendChild(header);
    holder.appendChild(card);

    document.getElementById("contentPane").appendChild(holder);
}

function createResultCard(title, address){
    let div = document.createElement('div');
    div.setAttribute("class", "col s12");
    let header = document.createElement('h6');
    let divH = document.createElement('div');
    divH.setAttribute("class", "card-horizontal");
    let divS = document.createElement('div');
    divS.setAttribute('class', "card-stacked");
    let divC = document.createElement('div');
    divC.setAttribute("class", "card-content");

    let txtTitle = document.createTextNode(title);
    let txtContent = document.createTextNode(address);
    let p = document.createElement('p');
    p.appendChild(txtContent);

    divC.appendChild(p);
    divS.appendChild(divC);
    divH.appendChild(divS);
    div.appendChild(divH);

    document.getElementById("contentPane").appendChild(div);
}

function createTabs(data){
    var passData = data;
    let tabsEle = document.getElementById("clientTabs");
    let clientT = document.getElementById("clientTab");
    let vehicleT = document.getElementById("vehicleTab");
    let insuranceT = document.getElementById("insuranceTab");
    var cp = document.getElementById("contentPane");
   
    // Client Tab info
    let l1 = document.createElement('p');
    l1.innerText = ("Name: " + data.name);
    let l9 = document.createElement('p');
    l9.setAttribute("id", "key");
    l9.innerText = ("Application Number: " + data.applicationNum);
    let l2 = document.createElement('p');
    l2.innerText = ("Address: " 
                    + data.address + " " 
                    + data.city + " " 
                    + data.state 
                    + " , " + data.zip);
    let l3 = document.createElement('p');
    l3.innerText = ("Phone: " + data.phone);
    let l4 = document.createElement('p');
    l4.innerText = ("Driver's License #: " + data.dlnumber);
    let l5 = document.createElement('p');
    l5.innerText = ("Date of birth: " + data.dob);
    let l6 = document.createElement('p');
    l6.innerText = ("License type: " + data.drivertype);
    let l7 = document.createElement('p');
    l7.innerText = ("Social Security Number: " + data.ssn);

    showEdit(l1, "name", "client", passData);
    showEdit(l2, "address", "client", passData);
    showEdit(l3, "phone", "client", passData);
    showEdit(l4, "dob", "client", passData);
    showEdit(l5, "drivertype", "client", passData);

    // Vehicle Tab Info
    vehicleT.innerHTML = '';
    if(data.vehicles == undefined){
        let div = document.createElement('div');
        let header = document.createElement('h6');
        header.innerText = "No vehicle information provided.";
        vehicleT.appendChild(header);
    }
    else {
        for(i = 0; i < data["vehicles"].length; i++){
            let div = document.createElement('div');
            let header = document.createElement('h6');
            header.innerText = "Vehicle " + (i+1) + ": ";
            let l1 = document.createElement('p');
            l1.innerText = ("Make: " + data["vehicles"][i].make);
            let l2 = document.createElement('p');
            l2.innerText = ("Model: " + data["vehicles"][i].model);
            let l3 = document.createElement('p');
            l3.innerText = ("Year: " + data["vehicles"][i].year);
            let l4 = document.createElement('p');
            l4.innerText = ("VIN: " + data["vehicles"][i].vin);
            vehicleT.appendChild(header);
            vehicleT.appendChild(l1);
            vehicleT.appendChild(l2);
            vehicleT.appendChild(l3);
            vehicleT.appendChild(l4);
        }
    }

    // Insurance Tab info
    insuranceT.innerHTML = '';
    let ins1 = document.createElement('p');
    ins1.innerText = ("Previous insurance company: " + data.prevInssurance);
    let ins2 = document.createElement('p');
    ins2.innerText = ("Vehicle incidents in the last five years: " + data.prevAccident);
    insuranceT.appendChild(ins1);
    insuranceT.appendChild(ins2);
    // Display
    clearCP();
    clientT.innerHTML = '';

    clientT.appendChild(l1);
    clientT.appendChild(l9);
    clientT.appendChild(l2);
    clientT.appendChild(l3);
    clientT.appendChild(l4);
    clientT.appendChild(l5);
    clientT.appendChild(l6);
    clientT.appendChild(l7);

    cp.appendChild(tabsEle);
    tabsEle.style.display = "block";
    
    /*
    item1.appendChild(clientTab);
    item2.appendChild(vehicleTab);
    item3.appendChild(insuranceTab);
    tabs.appendChild(item1);
    tabs.appendChild(item2);
    tabs.appendChild(item3);
    col.appendChild(tabs);
    divTabs.appendChild(col);
    divTabs.appendChild(clientDiv);
    divTabs.appendChild(vehicleDiv);
    divTabs.appendChild(insuranceDiv);
    
    /*
    contain.appendChild(clientDiv);
    contain.appendChild(vehicleDiv);
    contain.appendChild(insuranceDiv);
    divTabs.appendChild(contain);
    */
    //cp.appendChild(divTabs);
}

function buildApplicationCard(data, type){
    let name = "Name: " + data["name"];
    let dob = "DOB: " + data["dob"];
    let driverT = "Driver Type: " + data["drivertype"];
    let appNumber = "Application Number: " + data["applicationNum"];

     // create card element
     let holder = document.createElement('div');
     holder.setAttribute("class", "col s12");
     let header = document.createElement('span');
     let headerTxt = document.createTextNode(name);
     header.appendChild(headerTxt);
     let card = document.createElement("div");
     card.setAttribute("class", "card small blue-grey darken-1");
     card.setAttribute("id", "result");
     let stack = document.createElement("div");
     stack.setAttribute("class", "card-stacked");
     let content = document.createElement("div");
     content.setAttribute("class", "card-content white-text");
     let action = document.createElement("div");
     action.setAttribute("class", "card-action");
     let p = document.createElement("p");
     let ptxt = document.createTextNode(appNumber);
     let p2 = document.createElement("p");
     let p2txt = document.createTextNode(dob);
     let p3 = document.createElement("p");
     let p3txt = document.createTextNode(driverT);

    let viewBtn = document.createElement('btn');
    let btnTxt = document.createTextNode("View Application Details")
    viewBtn.setAttribute("class", "wave-effect waves-light btn");
    viewBtn.addEventListener("click", displayApplication.bind(null, data));
    viewBtn.appendChild(btnTxt);

    action.appendChild(viewBtn);
    p.appendChild(ptxt);
    p2.appendChild(p2txt);
    p3.appendChild(p3txt);
    
    content.appendChild(header);

    content.appendChild(p3);
    content.appendChild(p2);
    content.appendChild(p);
    content.appendChild(action);
    stack.appendChild(content);
    card.appendChild(stack);
    holder.appendChild(card);
    if(type == "open"){
        document.getElementById("openApps").appendChild(holder);
    }
    if(type == "submitted"){
        document.getElementById("newApps").appendChild(holder);
    }
    if(type == "closed"){
        document.getElementById("closedApps").appendChild(holder);
    }
}

function displayApplication(data){
    clearCP();
    let cp = document.getElementById("contentPane");
    let header = document.createElement('h5');
    header.classList.add("center-align");
    header.style.backgroundColor = "white";
    let headerTxt = document.createTextNode("Application Details: ");
    header.appendChild(headerTxt);
    let l1 = document.createElement('p');
    l1.innerText = ("Name: " + data["name"]);

    let l2 = document.createElement('p');
    l2.innerText = ("Address: " + data["address"] + " " + data["city"] + " " + data["state"] + " , " + data["zip"]);
    let l3 = document.createElement('p');
    l3.innerText = ("Phone: " + data["phone"]);
    let l4 = document.createElement('p');
    l4.innerText = ("Driver's License #: " + data["dlnumber"]);
    let l5 = document.createElement('p');
    l5.innerText = ("Date of birth: " + data["dob"]);
    let l6 = document.createElement('p');
    l6.innerText = ("License type: " + data["drivertype"]);
    let l7 = document.createElement('p');
    l7.innerText = ("Social Security Number: " + data["ssn"]);
    let l8 = document.createElement('p');
    l8.setAttribute("id", "key");
    l8.innerText = ("Application Number: " + data["applicationNum"]);
    let l9 = document.createElement('p');
    l9.innerText = ("Application Status: " + data["appStatus"]);

    showEdit(l1, "name", "app", data);
    showEdit(l2, "address", "app", data);
    showEdit(l3, "phone", "app", data);
    showEdit(l4, "dlnumber", "app", data);
    showEdit(l5, "dob", "app", data);
    showEdit(l6, "drivertype", "app", data);

    cp.appendChild(header);
    cp.appendChild(l8);
    cp.appendChild(l9);
    cp.appendChild(l1);
    cp.appendChild(l2);
    cp.appendChild(l3);
    cp.appendChild(l4);
    cp.appendChild(l5);
    cp.appendChild(l6);
    cp.appendChild(l7);

    let status = data["appStatus"];
    if(status == "submitted"){
        let btnDiv = document.createElement('div');
        btnDiv.classList.add('center-align');
        btnDiv.style.textAlign = "center";
        let openBtn = document.createElement('btn');
        let btnTxt = document.createTextNode("Open Application");
        openBtn.setAttribute("id", "openApplicationBtn");
        openBtn.style.width = "300px";
        openBtn.setAttribute("class", "wave-effect waves-light btn");
        openBtn.addEventListener("click", openApplication.bind(null, data));
        openBtn.appendChild(btnTxt);
        btnDiv.appendChild(openBtn);
        cp.appendChild(btnDiv);
    }
    else if(status == "open"){
        let acceptDiv = document.createElement('div');
        acceptDiv.classList.add('center-align');
        acceptDiv.style.textAlign = "center";

        let rejectDiv = document.createElement('div');
        rejectDiv.classList.add('center-align');
        rejectDiv.style.textAlign = "center";

        let acceptBtn = document.createElement('btn');
        let btnTxt = document.createTextNode("Accept New Client");
        acceptBtn.setAttribute("id", "openApplicationBtn");
        acceptBtn.style.width = "300px";
        acceptBtn.setAttribute("class", "wave-effect waves-light btn");
        acceptBtn.addEventListener("click", formatNewClient.bind(null, data));
        acceptBtn.appendChild(btnTxt);
        

        let rejectBtn = document.createElement('btn');
        let rejectTxt = document.createTextNode("Reject Application");
        rejectBtn.setAttribute("id", "rejectApplicationBtn");
        rejectBtn.style.width = "300px";
        
        rejectBtn.setAttribute("class", "center-align wave-effect waves-light btn");
        rejectBtn.addEventListener("click", rejectApplication.bind(null, data));
        rejectBtn.appendChild(rejectTxt);

        acceptDiv.appendChild(acceptBtn);
        rejectDiv.appendChild(rejectBtn);
        cp.appendChild(acceptDiv);
        cp.appendChild(rejectDiv);
    }
    else if(status == "closed"){
        let btnDiv = document.createElement('div');
        btnDiv.classList.add('center-align');
        btnDiv.style.textAlign = "center";

        let reOpenBtn = document.createElement('btn');
        let btnTxt = document.createTextNode("Re-Open Application");
        reOpenBtn.setAttribute("id", "reOpenApplicationBtn");
        reOpenBtn.style.width = "300px";
        reOpenBtn.setAttribute("class", "center-align wave-effect waves-light btn");
        reOpenBtn.addEventListener("click", openApplication.bind(null, data));
        reOpenBtn.appendChild(btnTxt);
        btnDiv.appendChild(reOpenBtn);
        cp.appendChild(btnDiv);
    }
}
function buildClaimCard(data, type){
    let name = "Name: " + data["name"];
    let polnum = "Policy Number: " + data["policyNum"];
    let claimNum = "Claim Number: " + data["claimNumber"];
    /*
    if(typeof data["location"] == "object"){
        var location = "Location: " + 
        data["location"]["latitude"] + '\xB0' + " Latitude " +
        data["location"]["longitude"] + '\xB0' + " Longitude";
    }
    else {
        var location = "Location: " + data["location"];
    }
    */
    //let location = "Location: " + data["location"];
    

    let date = "Date and Time: " + data.date;

    let holder = document.createElement('div');
    holder.setAttribute("class", "col s12");
    let header = document.createElement('span');
    let headerTxt = document.createTextNode(claimNum);
    header.appendChild(headerTxt);
    let card = document.createElement("div");
    card.setAttribute("class", "card small blue-grey darken-1");
    card.setAttribute("id", "result");
    let stack = document.createElement("div");
    stack.setAttribute("class", "card-stacked");
    let content = document.createElement("div");
    content.setAttribute("class", "card-content white-text");
    let action = document.createElement("div");
    action.setAttribute("class", "card-action");
    let p = document.createElement("p");
    let ptxt = document.createTextNode(name);
    let p2 = document.createElement("p");
    let p2txt = document.createTextNode(polnum);
    let p3 = document.createElement("p");
    let p3txt = document.createTextNode(date);

    let viewBtn = document.createElement('btn');
    let btnTxt = document.createTextNode("View Claim Details")
    viewBtn.setAttribute("class", "wave-effect waves-light btn");
    viewBtn.addEventListener("click", displayClaim.bind(null, data));
    viewBtn.appendChild(btnTxt);

    action.appendChild(viewBtn);
    p.appendChild(ptxt);
    p2.appendChild(p2txt);
    p3.appendChild(p3txt);
    
    content.appendChild(header);
    content.appendChild(p3);
    content.appendChild(p2);
    content.appendChild(p);
    content.appendChild(action);
    stack.appendChild(content);
    card.appendChild(stack);
    holder.appendChild(card);

    document.getElementById("claimsList").appendChild(holder);
}

function displayClaim(data){
    let tabEle = document.getElementById("claimTabs");
    let claimT = document.getElementById("claimInfoTab");
    let streetT = document.getElementById("streetViewTab");
    let photoT = document.getElementById("photosTab");
    var cp = document.getElementById("contentPane");

    // Claim info tab
    let l1 = document.createElement('p');
    l1.setAttribute("id", "key");
    l1.innerText = ("Claim Number: " + data["claimNumber"])
    let l2 = document.createElement('p');
    l2.innerText = ("Claim Status: " + data["claimStatus"]);
    let l3 = document.createElement('p');
    l3.innerText = ("Name: " + data["name"]);
    let l4 = document.createElement('p');
    l4.innerText = ("Date Submitted: " + data.date);
    let l5 = document.createElement('p');
    if(typeof data["location"] == "object"){
        var location = "Location: " + 
        data["location"]["latitude"] + '\xB0' + " Latitude " +
        data["location"]["longitude"] + '\xB0' + " Longitude";
    }
    else {
        var location = "Location: " + data["location"];
    }
    l5.innerText = (location);
    let l6 = document.createElement('p');
    l6.innerText = ("Description: " + data["description"]);
    let l7 = document.createElement('p');
    l7.innerText = ("Policy Number: " + data["policyNum"]);

    // Street View
    if(typeof data["location"] == "object"){
        var local = {lat: data["location"]["latitude"], lng: data["location"]["longitude"]};
    }
    else {
        var str = data.location;
        str1 = str.split(" and ")[0];
        str2 = str.split(" and ")[1];
        let coordX = Number(str1);
        let coordY = Number(str2);
        var local = {lat: coordX, lng: coordY};
    }
    showEdit(l3, "name", "claim", data);
    showEdit(l4, "date", "claim", data);
    showEdit(l5, "location", "claim", data);
    showEdit(l6, "description", "claim", data);

    claimT.innerHTML = '';
    claimT.appendChild(l1);
    claimT.appendChild(l2);
    claimT.appendChild(l3);
    claimT.appendChild(l4);
    claimT.appendChild(l5);
    claimT.appendChild(l6);
    claimT.appendChild(l7);
    
    let imgDiv = document.createElement('div');
    imgDiv.style.textAlign ="center";
    let img = document.createElement('img');
    img.setAttribute("id", "claimPhoto");
    //img.src = getClaimPhotos();
    imgDiv.appendChild(img);
    photoT.appendChild(imgDiv);
    img.src = getClaimPhotos(img, data.claimNumber);
    

    // Display info
    clearCP();
    initializeMap(local);
    let mapEle = document.getElementById("map-canvas");
    let panoEle = document.getElementById("pano");
    streetT.appendChild(mapEle);
    streetT.appendChild(panoEle);
    //streetT.appendChild(panoEle);
    cp.appendChild(tabEle);
    tabEle.style.display = "block";
    mapEle.style.display = "block";
    //panoEle.style.display = "block";
    //panoEle.style.visibility = "hidden";
    //google.maps.event.addDomListener(window, "load", initializeMap);
    
    //panoEle.style.display = "block";
    //onsole.log("Displaying claim: ");
    //console.log(data); 
    /*
    let imgDiv = document.createElement('div');
    imgDiv.style.alignContent ="center";
    let img = document.createElement('img');
    img.src = getClaimPhotos();
    imgDiv.appendChild(img);
    photoT.appendChild(imgDiv);
    */
}

function toggleMapView(){
    var tabEle = document.getElementById("streetViewTab");
    var mapEle = document.getElementById("map-canvas");
    var panoEle = document.getElementById("pano");
    if(mapEle.style.display == "block"){
        mapEle.style.display = "none";
        panoEle.style.display = "block";
        tabEle.appendChild(panoEle);
    }
    else {
        panoEle.style.display = "none";
        mapEle.style.display = "block";
        tabEle.appendChild(mapEle);
    }
}

// Clears content pane for loading new content
function clearCP() {
    let cp = document.getElementById("contentPane");
    while(cp.firstElementChild){
        let current = cp.firstChild;
        current.style.display = "none";
        cp.removeChild(cp.firstElementChild);
        document.getElementById("body").appendChild(current);
    }
    document.getElementById("map-canvas").style.display = "none";
    document.getElementById("pano").style.display = "none";
}
function showAlert(alertText){
    let p = document.createElement('p');
    let txt = document.createTextNode(alertText);
    p.setAttribute("class", "alert");
    p.style.textAlign = "center";
    p.appendChild(txt);
    document.getElementById("contentPane").appendChild(p);
}