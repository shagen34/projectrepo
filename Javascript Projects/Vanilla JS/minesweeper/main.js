var grid;
var mines;
var flags = 0;
var charFlag = '!';
var adjCells = new Array();
var maxCoord;
var placedMines = 0;

function start(gridSize, numMines){
    diffSelect.style.display = 'none';
    createBoard(gridSize);
    mines = numMines;
    maxCoord = gridSize;
}

function createBoard(size){
    var board = document.getElementById("board");
    for(i=0; i<size; i++){
        for(j=0; j<size; j++){
            var newTile = document.createElement('div');
            board.appendChild(newTile);
            newTile.setAttribute('class', 'tile');
            newTile.setAttribute('board-row', i+1);
            newTile.setAttribute('board-col', j+1);
            var tID = (i+1) + " " + (j+1);
            newTile.setAttribute('id', tID);
            newTile.setAttribute('onclick', 'newMove(id)');
            newTile.setAttribute('isMine', 'false');
            newTile.setAttribute('isChecked', 'false');
        }
        var lineBreak = document.createElement('div');
        board.appendChild(lineBreak);
        lineBreak.className = 'linebr';
    }
}

function newMove(coord){
    var t = document.getElementById(coord);
    if(t.className == 'openTile'){
        //do nothing, tile already opened
    }
    else if(placedMines == 0){
        t.setAttribute('class', 'openTile');
        placeMines(mines);
        reveal(coord);
    }
    else {
        t.setAttribute('class', 'openTile');
        reveal(coord);
        var allT = document.querySelectorAll(".tile");
        if(allT.length == 0){
            var clear = document.createElement('h1');
            var txt = document.createTextNode("You win!");
            clear.appendChild(txt);
            document.getElementById("canvas").appendChild(clear);
            var can = document.getElementById("canvas")
            can.setAttribute('class', 'no-clicks');
        }
    }
}

function reveal(coord){
    var tile = document.getElementById(coord);
    if(tile.getAttribute('isMine') == 'true'){
        var clear = document.createElement('h1');
        var txt = document.createTextNode("You hit a bomb! Game over");
        clear.appendChild(txt);
        document.getElementById("canvas").appendChild(clear);
        tile.setAttribute('class', 'mineExplode');
        var can = document.getElementById("canvas")
        can.setAttribute('class', 'no-clicks');
    }
    else {
        var numAdj = adjacent(coord);
        var na = document.createTextNode(numAdj);
        tile.appendChild(na);
    }
}


function adjacent(coords){
    var numAdj = 0;
    var getCoords = coords.toString().split(" ");
    var toCheck;

    var xCheck, yCheck;

    // left
    xCheck = parseInt(getCoords[0]) - 1;
    yCheck = parseInt(getCoords[1]);
    if(xCheck > 0){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    
    // right
    xCheck = parseInt(getCoords[0]) + 1;
    yCheck = parseInt(getCoords[1]);
    if(xCheck <= maxCoord){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    // below
    xCheck = parseInt(getCoords[0]);
    yCheck = parseInt(getCoords[1]) + 1;
    toCheck = xCheck + " " + yCheck;
    if(yCheck <= maxCoord){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    // above
    xCheck = parseInt(getCoords[0]);
    yCheck = parseInt(getCoords[1]) - 1;
    toCheck = xCheck + " " + yCheck;
    if(yCheck > 0){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    // top-left
    xCheck = parseInt(getCoords[0]) - 1;
    yCheck = parseInt(getCoords[1]) - 1;
    toCheck = xCheck + " " + yCheck;
    if(yCheck > 0 && xCheck > 0){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    // top-right
    xCheck = parseInt(getCoords[0]) + 1;
    yCheck = parseInt(getCoords[1]) - 1;
    toCheck = xCheck + " " + yCheck;
    if(xCheck <= maxCoord && yCheck > 0){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    // bot-left
    xCheck = parseInt(getCoords[0]) - 1;
    yCheck = parseInt(getCoords[1]) + 1;
    toCheck = xCheck + " " + yCheck;
    if(xCheck > 0 && yCheck <= maxCoord){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    // bot-right
    xCheck = parseInt(getCoords[0]) + 1;
    yCheck = parseInt(getCoords[1]) + 1;
    toCheck = xCheck + " " + yCheck;
    if(xCheck <= maxCoord && yCheck <= maxCoord){
        toCheck = xCheck + " " + yCheck;
        
        if(checkTile(toCheck)){
            numAdj++;
        }
    }
    //console.log(numAdj);
    return numAdj;
}

function checkTile(coord){
    //console.log("check tile at " + coord);
    if(document.getElementById(coord).getAttribute('isMine') == 'true'){
        //console.log("issa mine");
        return true;
    }
    return false;
}

function revealNeighbor(coord){
    var n = document.getElementById(coord);
    if(adjacent(coord) == 0 && n.getAttribute('class') == 'tile'){
        n.setAttribute('class', 'openTile');
    }
}
function placeMines(mines){
    while(placedMines < mines){
        var x = Math.floor(Math.random()*maxCoord + 1);
        var y = Math.floor(Math.random()*maxCoord + 1);
        var tileID = x + " " + y;
        var tile = document.getElementById(tileID);
        if(tile.getAttribute('isMine') == 'false' && tile.getAttribute('class') == 'tile'){
            tile.setAttribute('isMine', 'true');
            tile.setAttribute('class', 'mine');
            placedMines++;
        }
    }
}
