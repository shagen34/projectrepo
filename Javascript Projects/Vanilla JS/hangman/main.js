    // Main script for hangman game
    var guesses = 0;
    var wrong = 0;
    var word = "";
    var alphabet = /^[A-Za-z]+$/;
    var guessBank = new Array();

    function start(diff) {
        diffSelection.style.display = 'none';
        var rand = Math.floor((Math.random() * 15) + 1);
        switch(diff){
            case 1: word = returnEasyWord(rand); break;
            case 2: word = returnNormalWord(rand); break;
            case 3: word = returnHardWord(rand); break;
        }
        loadImg();
        loadCanvas();
    }

    function loadImg(){
        var img = document.createElement("img");
        currentImg = 'assets/' + wrong + '.jpg';
        img.src = currentImg;
        img.setAttribute("height", "350");
        img.setAttribute("width", "200");
        img.setAttribute("alt", wrong + " wrong guess hangman image");
        document.getElementById("img").innerHTML = "";
        document.getElementById("img").appendChild(img);
    }

    function loadCanvas(){
        var options = document.createElement('div');
        options.id = "optionMenu";
        var reset = document.createElement('btn');
        reset.id = "resetbtn";
        var txt = document.createTextNode("New Game");
        reset.setAttribute("class", "wave-effect waves-light btn");
        reset.setAttribute("onclick", "window.location.reload(); guesses=0;");
        reset.appendChild(txt);
        options.appendChild(reset);
        document.getElementById("reset").appendChild(options);
        
        
        document.getElementById("guess").style.visibility = "visible";
        document.getElementById("guessBank").style.visibility = "visible";
        document.getElementById("youG").style.visibility = "visible";
        document.getElementById("gL").style.visibility = "visible";
        document.getElementById("current").style.visibility = "visible";
        var submit = document.createElement('btn');
        submit.id = "submitBtn";
        var subtxt = document.createTextNode("Submit Guess");
        submit.setAttribute("class", "wave-effect waves-light btn");
        submit.setAttribute("onclick", "newGuess()");
        
        submit.appendChild(subtxt);
        options.appendChild(submit);
        document.getElementById("submit").appendChild(submit);

        var wordIs = document.createTextNode("The word is: ");
        var wordIsTxt = document.createElement('h6');
        wordIsTxt.appendChild(wordIs);
        document.getElementById("currLabel").appendChild(wordIsTxt);

        

        loadWord();
    }

    function listenForEnter(e){
        e = e || window.event;
        if(e.keyCode == 13){
            document.getElementById('submitBtn').click();
            return false;
        }
        return true;
    }

    function loadWord(){
        var i = word.length;
        var current = '';

        if(guesses == 0){
            for(j=0; j < i; j++){
                current += "_ ";
            }
            displayWord(current);
        }
        else{
            var lastGuess = guessBank[guessBank.length-1];
            var match = word.match(lastGuess);
            if(match == null){
                wrong++;
                if(wrong == 10)
                {
                    loseGame();
                }
            }
            currentPos = 0;
            for(x=0; x<i; x++){
                for(p=0; p<guessBank.length; p++){
                    if(word[x] == guessBank[p]){
                        // fill correct guesses
                        current += guessBank[p] + " ";
                        currentPos++;
                    }
                }
                if(currentPos == x){
                    current += "_ ";
                    currentPos++;
                }
            }
            displayWord(current);
            loadImg();
            var foundWord = current.match("_");
            if(foundWord == null)
            {
                winGame();
            }
        }
    }

    function loadGuessBank(){
        var comp = document.getElementById("guessBank");
        comp.innerHTML = "";
        var str = "";
        for (let value of guessBank){
            str += value + ", ";
        }
        str = str.slice(0, -2);
        var bank = document.createElement('h6');
        var bankTxt = document.createTextNode(str);
        bank.appendChild(bankTxt);
        comp.appendChild(bank);
    }

    function displayWord(current){
        document.getElementById("current").innerHTML = "";
        var wordStatus = document.createTextNode(current);
        var wordTxt = document.createElement('h3');
        wordTxt.appendChild(wordStatus);
        document.getElementById("current").appendChild(wordTxt);
    }

    function newGuess(){
        clearAlert();
        var box = document.getElementById("guessBox");
        var g = box.value;
        var valid = g.match(alphabet);
        var alreadyG = guessBank.find(function(element){
            return element == g;
        });

        if(g.length > 1){
            alert("One letter at a time!");
            box.value = null;
        }
        else if(valid == null){
            alert("Your guess must be a letter!");
            box.value = null;
        }
        else if(alreadyG != null){
            alert("You already guessed this letter!");
            box.value = null;
        }
        else {
            guessBank.push(g);
            guesses++;
            box.value = null;
            loadGuessBank();
            loadWord();
        }
    }

    function alert(alertText){
        clearAlert();
        var alert = document.createElement('i');
        alert.setAttribute('class', "material-icons");
        alert.innerHTML = 'report_problem';
        var alertTxt = document.createTextNode(alertText);
        var alertN = document.createElement('h6');
        alertN.appendChild(alertTxt);
        alertN.style.color = 'red';
        document.getElementById("notify").appendChild(alert);
        document.getElementById("notify").appendChild(alertN);
    }

    function clearAlert(){
        alertSec = document.getElementById("notify");
        alertSec.innerHTML = "";
    }
    //credit to knowyourmeme.com for the pepe images
    function winGame(){
        var canvas = document.getElementById("mainCanvas");
        canvas.innerHTML = "";
        var titleBox = document.createElement('div');
        titleBox.setAttribute("class", "col s12 center-align");
        var title = document.createElement('h3');
        var titleText = document.createTextNode("You Won!");
        title.appendChild(titleText);
        titleBox.appendChild(title);
        document.getElementById("mainCanvas").appendChild(titleBox);

        var fgm = document.createElement("img");
        loc = 'assets/feelsgoodman.png';
        fgm.src = loc;
        fgm.setAttribute("height", "300");
        fgm.setAttribute("width", "500");
        fgm.setAttribute("alt", "You win, FeelsGoodMan");
        var imgBox = document.createElement('div');
        imgBox.setAttribute("class", "col s12 center-align");
        imgBox.appendChild(fgm);
        document.getElementById("mainCanvas").appendChild(imgBox);
    }

    function loseGame(){
        var canvas = document.getElementById("mainCanvas");
        canvas.innerHTML = "";
        var titleBox = document.createElement('div');
        titleBox.setAttribute("class", "col s12 center-align");
        var title = document.createElement('h3');

        var txt = "You lost! The word was '" + word + "'";

        var titleText = document.createTextNode(txt);
        title.appendChild(titleText);
        titleBox.appendChild(title);
        document.getElementById("mainCanvas").appendChild(titleBox);

        var fgm = document.createElement("img");
        loc = 'assets/pepeLose.gif';
        fgm.src = loc;
        fgm.setAttribute("height", "300");
        fgm.setAttribute("width", "400");
        fgm.setAttribute("alt", "You win, FeelsGoodMan");
        var imgBox = document.createElement('div');
        imgBox.setAttribute("class", "col s12 center-align");
        imgBox.appendChild(fgm);
        document.getElementById("mainCanvas").appendChild(imgBox);
    }
    
    function returnEasyWord(index) {
        switch(index){
            case 1: return "apple"; break;
            case 2: return "rose"; break;
            case 3: return "leak"; break;
            case 4: return "box"; break;
            case 5: return "banana"; break;
            case 6: return "band"; break;
            case 7: return "school"; break;
            case 8: return "year"; break;
            case 9: return "football"; break;
            case 10: return "soccer"; break;
            case 11: return "king"; break;
            case 12: return "queen"; break;
            case 13: return "place"; break;
            case 14: return "time"; break;
            case 15: return "watch"; break;
        }
    }

    function returnNormalWord(index) {
        switch(index){
            case 1: return "definitely"; break;
            case 2: return "restaurant"; break;
            case 3: return "friend"; break;
            case 4: return "cancelled"; break;
            case 5: return "necessary"; break;
            case 6: return "statue"; break;
            case 7: return "champion"; break;
            case 8: return "general"; break;
            case 9: return "rocket"; break;
            case 10: return "cartoon"; break;
            case 11: return "adventure"; break;
            case 12: return "square"; break;
            case 13: return "alligator"; break;
            case 14: return "vitamin"; break;
            case 15: return "zombie"; break;
        } 
    }
    
    function returnHardWord(index) {
        switch(index){
            case 1: return "awkward"; break;
            case 2: return "bagpipes"; break;
            case 3: return "banjo"; break;
            case 4: return "crypt"; break;
            case 5: return "gazebo"; break;
            case 6: return "rhythm"; break;
            case 7: return "ivory"; break;
            case 8: return "jukebox"; break;
            case 9: return "memento"; break;
            case 10: return "oxygen"; break;
            case 11: return "pixel"; break;
            case 12: return "rogue"; break;
            case 13: return "zephyr"; break;
            case 14: return "zombie"; break;
            case 15: return "lynx"; break;
        }
    }