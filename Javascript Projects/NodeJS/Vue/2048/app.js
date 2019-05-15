new Vue({
    el:'#game-app',
    data: {
        scoreList: 1,
        score: 10000,
        moveOccur: true
    },
    mounted () {
        document.addEventListener("keyup", this.newMove);
        this.initBoard();
        this.hiscoreList();
    },
    methods: {
        // 4x4 grid
        initBoard: function() {
            this.placeTile(2);
            this.placeTile(2);
        },
        setColor: function(tile){
            var text;
            if(tile.style.visibility == "hidden"){
                text = tile.textContent;
            }
            else {
                text = tile.innerText;
            }
            if(text == ''){
                tile.style.backgroundColor = "#bdbdbd";
            }
            if(text == '2'){
                tile.style.backgroundColor = "#fff3e0";
            }
            if(text == '4'){
                tile.style.backgroundColor = "#ffb74d";
            }
            if(text == '8'){
                tile.style.backgroundColor = "#f57c00";
            }
            if(text == '16'){
                tile.style.backgroundColor = "#ef5350";
            }
            if(text == '32'){
                tile.style.backgroundColor = "#f4511e";
            }
            if(text == '64'){
                tile.style.backgroundColor = "#c5e1a5";
            }
            if(text == '128'){
                tile.style.backgroundColor = "#00897b";
            }
            if(text == '256'){
                tile.style.backgroundColor = "#1de9b6";
            }
            if(text == '512'){
                tile.style.backgroundColor = "#8bc34a";
            }
            if(text == '1024'){
                tile.style.backgroundColor = "#4caf50";
            }
            if(text == '2048'){
                tile.style.backgroundColor = "#69f0ae";
            }
            if(text == '4096'){
                tile.style.backgroundColor = "#18ffff";
            }
        },
        getColor: function(tile){
            let text = tile.innerText;
            if(text == ''){
                return "#bdbdbd";
            }
            if(text == '2'){
                return "#fff3e0";
            }
            if(text == '4'){
                return "#ffb74d";
            }
            if(text == '8'){
                return "#f57c00";
            }
            if(text == '16'){
                return "#ef5350";
            }
            if(text == '32'){
                return "#f4511e";
            }
            if(text == '64'){
                return "#c5e1a5";
            }
            if(text == '128'){
                return "#00897b";
            }
            if(text == '256'){
                return "#1de9b6";
            }
            if(text == '512'){
                return "#8bc34a";
            }
            if(text == '1024'){
                return "#4caf50";
            }
            if(text == '2048'){
                return "#69f0ae";
            }
            if(text == '4096'){
                return "#18ffff";
            }
        },
        checkBoard: function(){
            if(document.querySelectorAll(".tile").length == 0 && this.moveOccur == false){
                this.endGame('loss');
                return;
            }
        },
        placeTile: function(num){
            // If no open tiles, game lost
            
            
            let placementX = Math.floor(Math.random()* 4) + 1;
            let placementY = Math.floor(Math.random()* 4) + 1;
            var onTile = placementX + "x" + placementY;
            var tileEle = document.getElementById(onTile);

            // Place only on unoccupied tiles
            while(tileEle.classList.contains("scoreTile")){
                let placementX = Math.floor(Math.random()* 4) + 1;
                let placementY = Math.floor(Math.random()* 4) + 1;
                onTile = placementX + "x" + placementY;
                tileEle = document.getElementById(onTile);
            }
            tileEle.style.visibility = "hidden";
            tileEle.className = "";
            tileEle.classList.add("scoreTile");
            tileEle.textContent = num;
            this.setColor(tileEle);
            
            $(tileEle).css({opacity: 0.0, visibility: "visible"})
            .animate({opacity: 1.0});
            
        },
        findNewPosition: function(tile, direction){
            // Parse data for finding new position
            var id = tile.id;
            var x = id.slice(0,1);
            x = parseInt(x);
            var y = id.slice(2,3);
            y = parseInt(y);

            // Find new position
            if(direction == 'up'){
                var check = y;
                while(check < 4){
                    check++;
                    //console.log("checking: " + x + "x" + check);
                    let checkEle = document.getElementById(x + "x" + check);

                    if(checkEle.classList.contains("scoreTile")){
                        let checkNum = checkEle.innerText;
                        let currNum = tile.innerText;
                        if(checkNum == currNum){
                            // combine tiles
                            this.combineTiles(tile, checkEle);
                            this.moveOccur = true;
                            return "combined";
                        }
                        //console.log("tile above is occupied!")
                        else {
                            let newPos = x + "x" + (check-1);
                            if(id != newPos){
                                this.moveOccur = true;
                            }
                            return newPos;
                        }
                    }
                }
                //console.log(this.moveOccur);
                let newPos = x + "x" + check;
                if(id != newPos){
                    this.moveOccur = true;
                }
                //console.log(this.moveOccur);
                return newPos;
            }
            if(direction == 'down'){
                var check = y;
                while(check > 1){
                    check--;
                    //console.log("checking: " + x + "x" + check);
                    let checkEle = document.getElementById(x + "x" + check);

                    if(checkEle.classList.contains("scoreTile")){
                        let checkNum = checkEle.innerText;
                        let currNum = tile.innerText;
                        if(checkNum == currNum){
                            // combine tiles
                            this.combineTiles(tile, checkEle);
                            this.moveOccur = true;
                            return "combined";
                        }
                        //console.log("tile below is occupied!")
                        else {
                            let newPos = (x + "x" + (check+1));
                            if(id != newPos){
                                this.moveOccur = true;
                            }
                            return newPos;
                        }
                    }
                }
                let newPos = (x + "x" + check);
                if(id != newPos){
                    this.moveOccur = true;
                }
                return newPos;
            }
            if(direction == 'left'){
                var check = x;
                while(check > 1){
                    check--;
                    //console.log("checking: " + check + "x" + y);
                    let checkEle = document.getElementById(check + "x" + y);

                    if(checkEle.classList.contains("scoreTile")){
                        let checkNum = checkEle.innerText;
                        let currNum = tile.innerText;
                        if(checkNum == currNum){
                            // combine tiles
                            this.combineTiles(tile, checkEle);
                            this.moveOccur = true;
                            return "combined";
                        }
                        //console.log("tile left is occupied!")
                        else {
                            let newPos = ((check+1) + "x" + y);
                            if(id != newPos){
                                this.moveOccur = true;
                            }
                            return newPos;
                        }
                    }
                }
                let newPos = (check + "x" + y);
                if(id != newPos){
                    this.moveOccur = true;
                }
                return newPos;
            }
            if(direction == 'right'){
                var check = x;
                while(check < 4){
                    check++;
                    //console.log("checking: " + check + "x" + y);
                    let checkEle = document.getElementById(check + "x" + y);

                    if(checkEle.classList.contains("scoreTile")){
                        let checkNum = checkEle.innerText;
                        let currNum = tile.innerText;
                        if(checkNum == currNum){
                            // combine tiles
                            this.combineTiles(tile, checkEle);
                            this.moveOccur = true;
                            return "combined";
                        }
                        //console.log("tile right is occupied!")
                        else {
                            let newPos = ((check-1) + "x" + y);
                            if(id != newPos){
                                this.moveOccur = true;
                            }
                            return newPos;
                        }
                    }
                }
                let newPos = (check + "x" + y);
                if(id != newPos){
                    this.moveOccur = true;
                }
                return newPos;
            }
        },
        shiftTile: function(tile, position){
            let num = tile.innerText;
            tile.innerText = '';
            tile.classList = '';
            tile.classList.add("tile");
            this.setColor(tile);

            let newT = document.getElementById(position);
            newT.classList = '';
            newT.classList.add("scoreTile");
            newT.innerText = num;
            this.setColor(newT);
        },
        combineTiles: function(tileShift, tileOG){
            let num = tileShift.innerText;
            num = parseInt(num);
            num = num*2;
            this.score += num;

            tileShift.innerText = '';
            tileShift.classList = '';
            tileShift.classList.add("tile");
            this.setColor(tileShift);

            tileOG.innerText = num;
            this.setColor(tileOG);
            this.moveOccur = true;
            if(num == 2048){
                this.endGame("win");
            }
        },
        move: function(direction) {
            var sTiles = document.getElementsByClassName("scoreTile");
            

            // Have to organize tiles to shift according to direction
            // e.g for a down move the tiles on the bottom shift first
            var orderTiles = [];

            // getElements already order for up move to work as intended
            if(direction == 'up'){
                for(ele of sTiles){
                    orderTiles.push(ele);
                }
            }
            if(direction == 'down'){
                var checkRow = 1;
                while(checkRow < 5){
                    for(ele of sTiles){
                        let id = ele.id;
                        let yComp = id.slice(2,3);
                        if(yComp == checkRow){
                            orderTiles.push(ele);
                        }
                    }
                    checkRow++;
                }
            }
            if(direction == 'left'){
                var checkCol = 1;
                while(checkCol < 5){
                    for(ele of sTiles){
                        let id = ele.id;
                        let xComp = id.slice(0,1);
                        if(xComp == checkCol){
                            orderTiles.push(ele);
                        }
                    }
                    checkCol++;
                }
            }
            if(direction == 'right'){
                var checkCol = 4;
                while(checkCol > 0){
                    for(ele of sTiles){
                        let id = ele.id;
                        let xComp = id.slice(0,1);
                        if(xComp == checkCol){
                            orderTiles.push(ele);
                        }
                    }
                    checkCol--;
                }
            }
            this.moveOccur = false;
            for(el of orderTiles){
                let pos = this.findNewPosition(el, direction);
                
                // shift tiles if they werent combined
                if(pos != "combined"){
                    this.shiftTile(el, pos);
                }
            }
            
            // place new tile
            //console.log(this.moveOccur);
            if(this.moveOccur == true){
                var num = 2;
                let rand = Math.floor(Math.random()*100) + 1;
                // 25% chance for tile to be a 4
                if(rand < 25){
                    num = 4;
                }
                this.placeTile(num);
            }
            else {
                this.checkBoard();
            }
            
        },
        // game ends when no tiles can move
        endGame: function(result){
            var scoreArr = [];
            var count = 0;
            let ref = db.collection("hiscores").orderBy("score", "desc").limit(10).get().then((snapshot) => {
                snapshot.docs.forEach(doc => {
                    //console.log(doc.data().score);
                    scoreArr.push(doc.data().score);
                })
            })
            if(result == 'loss'){
                
                let contain = document.createElement("div");
                contain.setAttribute("class", "row center-align");
                let header = document.createElement('h5');
                let txt = document.createTextNode("You lost!");
                
                /*
                if(this.score > scoresList.slice(-1)[0]){
                    var ret = prompt("You made it to the hiscores list! Enter your name: ", "your name here");
                    document.write("You entered: " + ret);
                }
                */
                console.log(scoreArr);
                console.log("We have this array but can't use it for some reason, maybe because of async")
                console.log("You lost");
            }
            else {
                console.log("You Won!");
            }
        },
        newGame: function(){
            this.score = 0;
            var activeTiles = document.querySelectorAll(".scoreTile");
            for(ele of activeTiles){
                ele.innerText = '';
                ele.className = '';
                ele.classList.add("tile");
                this.setColor(ele);
            }
            this.initBoard();
        },
        newMove: function(){
            if(event.keyCode == 37){
                this.move('left');
            }
            else if(event.keyCode == 38){
                this.move('up');
            }
            else if(event.keyCode == 39){
                this.move('right');
            }
            else if(event.keyCode == 40){
                this.move('down');
            }
        },
        hiscoreList: function(){
            this.scoreList = 1;
            let hicontainer = document.createElement('div');
            hicontainer.setAttribute("id", "hiscores");
            hicontainer.setAttribute("class", "row center-align");
            let header = document.createElement('h5');
            let txt = document.createTextNode("Hiscores List");
            header.appendChild(txt);
            hicontainer.appendChild(header);
            document.getElementById("boardContainer").appendChild(hicontainer);
            let ref = db.collection("hiscores").orderBy("score", "desc").limit(10).get().then((snapshot) => {
                snapshot.docs.forEach(doc => {
                    this.displayHiscores(doc.data());
                });
            })
        },
        displayHiscores: function(data){
            let list = document.getElementById("hiscores");

            let nameDiv = document.createElement('div');
            nameDiv.setAttribute("class", "col s6 right-align");
            nameDiv.innerText = this.scoreList + ". " + data.name + ":";
            let scoreDiv = document.createElement('div');
            scoreDiv.setAttribute("class", "col s6 left-align");
            scoreDiv.innerText = data.score;
            list.appendChild(nameDiv);
            list.appendChild(scoreDiv);
            this.scoreList++;

            //console.log(data);
        }
    }
});