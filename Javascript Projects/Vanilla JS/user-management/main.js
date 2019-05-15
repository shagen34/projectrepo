window.onload = function() {
    // We will generate 60 users for 10 rows of users to filter
    var loadedCards = 0;
    var totalCards = 60;
        for(i=0; i < totalCards; i++){
            $.ajax({
                url: 'https://randomuser.me/api/',
                dataType: 'json',
                success: function(data) {
                    generateCard(data.results);
                }
            });
        }

    function generateCard(userData){
        // Format Name
        let firstName = makeProper(userData[0].name.first);
        let lastName = makeProper(userData[0].name.last);
        let fullName = firstName + " " + lastName;

        let age = userData[0].dob.age;
        let gender = makeProper(userData[0].gender);
        let location = makeProper(userData[0].location.city);

        let profilePic = userData[0].picture.large;

        // Create DOM elements
        let cardHolder = document.createElement('div');
        cardHolder.setAttribute("class", "col s2 center-align");
        let lineb = document.createElement('br');
        let newCard = document.createElement('div');
        newCard.setAttribute("class", "card");
        let imgDiv = document.createElement('div');
        imgDiv.setAttribute("class", "card-image");
        let img = document.createElement('img');
        img.setAttribute("src", profilePic);
        let cardTitle = document.createElement('span');
        let titleTxt = document.createTextNode(fullName);
        let cardCont = document.createElement('div');
        cardCont.setAttribute("class", "card-content");
        let contentP1 = document.createElement('p');
        let contentTxt1 = document.createTextNode(
            "Age: " + age
        );
        let contentP2 = document.createElement('p');
        let contentTxt2 = document.createTextNode(
            "Gender: " + gender
        );
        let contentP3 = document.createElement('p');
        contentP3.setAttribute("class", "truncate");
        let contentTxt3 = document.createTextNode(
            "City: " + location
        );

        let canvas = document.getElementById("userCanvas");
        
        contentP1.appendChild(contentTxt1);
        contentP2.appendChild(contentTxt2);
        contentP3.appendChild(contentTxt3);
        cardCont.appendChild(contentP1);
        cardCont.appendChild(contentP2);
        cardCont.appendChild(contentP3);
        cardTitle.appendChild(titleTxt);
        imgDiv.appendChild(img);
        imgDiv.appendChild(cardTitle);
        newCard.appendChild(imgDiv);
        newCard.appendChild(cardCont);
        cardHolder.appendChild(newCard);

        canvas.appendChild(cardHolder);
        loadedCards++;
        if(loadedCards == totalCards){
            document.getElementById("genNotify").style.display = "none";
            canvas.style.display = "inline";
        }
    }

    function makeProper(name){
        return name.charAt(0).toUpperCase() + name.slice(1);
    }
}

function filterUsers(){
    var input = document.getElementById("filter");
    var value = input.value;
    console.log("Filtering by " + value + "... ");
    var canvas = document.getElementById("userCanvas");
    var cards = canvas.getElementsByClassName("card");
    for(i=0; i<cards.length; i++){
        let inspect = cards[i];
        let lowerCase = inspect.textContent.toLowerCase();
        if(inspect.textContent.includes(value) || lowerCase.includes(value)){
            inspect.parentElement.style.display = "";
        }
        else{
            inspect.parentElement.style.display = "none";
        }
    }
}

