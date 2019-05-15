window.onload = function () {
    
    
    var totalPosts = 10;
    var postNo =  0;
    //Generate 10 posts
    for(i=0; i< totalPosts; i++){
        let rand = randomNum(100);
        let get = 'https://jsonplaceholder.typicode.com/posts/' + rand;

        $.ajax({
            url: get,
            dataType: 'json',
            success: function(data) {
                //console.log(data);
                generate(data);
            }
        });
    }

    function generate(postData){
        let get = 'https://jsonplaceholder.typicode.com/users/' + postData.userId
        //console.log(user);
        $.ajax({
            url: get,
            dataType: 'json',
            success: function(data){
                generatePost(postData, data);
            }
        })
    }

    function generatePost(postData, userData){
        // For unique element ids
        postNo++;
        // Building DOM elements for post
        let postHolder = document.createElement('div');
        postHolder.setAttribute("class", "col s12 left-align");
        let newPost = document.createElement('div');
        newPost.setAttribute("class", "card blue-grey darken-1");
        let titleHolder = document.createElement('div');
        let postTitle = document.createElement('h4');
        postTitle.setAttribute("color", "white");
        titleHolder.setAttribute("class", "center-align")
        let titleTxt = document.createTextNode(postData.title);
        let postContent = document.createElement('div');
        postContent.setAttribute("class", "card-content white-text");
        let postUser = document.createElement('h6');
        let userTxt = document.createTextNode("User " + userData.username + " said: ");
        let postTxt = document.createTextNode(postData.body);

        let btnHolder = document.createElement('div');
        //btnHolder.setAttribute("class", "col s12 left-align");
        let commentBtn = document.createElement('a');
        commentBtn.innerText = "Show Comments";
        commentBtn.setAttribute("id", "commentBtn " + postNo);
        commentBtn.setAttribute("class", "wave-effect wave=light btn");
        commentBtn.setAttribute("onclick", "expandComments(this.id)")


        let canvas = document.getElementById("postCanvas");
        postUser.appendChild(userTxt);
        postContent.appendChild(postUser);
        postContent.appendChild(postTxt);
        btnHolder.appendChild(commentBtn);
        postContent.appendChild(btnHolder);
        postTitle.appendChild(titleTxt);
        titleHolder.appendChild(postTitle);
        newPost.appendChild(titleHolder);
        newPost.appendChild(postContent);
        postHolder.appendChild(newPost);
        canvas.appendChild(postHolder);


    }
}
function expandComments(postId){

        let hold = document.getElementById(postId);
        let p = hold.parentElement.parentElement.parentElement;
        // gets entire post element
    if(p.style.height == '600px'){
        $(p).animate({
            height: '195px'
        });
        hideComments(p);
        document.getElementById(postId).innerText = "Show Comments";
    }
    else {
        $(p).animate({
            height: '600px'
        });
        getComments(p);
        document.getElementById(postId).innerText = "Hide Comments";
    }
}
function getComments(post){
    if(commsCheck(post)){
        for(i=0; i<post.childNodes.length; i++){
            if(post.childNodes[i].id == "coho"){
                post.childNodes[i].style.display = "inline";
            }
        }
    }
    else{
        // Else Generate 3 comments
        let comments = document.createElement('div');
        comments.setAttribute("id", "comms");
        post.appendChild(comments)
        for(i=0; i < 3; i++){
            let rand = randomNum(500);
            let get = 'https://jsonplaceholder.typicode.com/comments/' + rand;

            $.ajax({
                url: get,
                dataType: 'json',
                success: function(data) {
                    //console.log(data);
                    generateComments(data, post);
                }
            });
        }
    }
}

function generateComments(commentData, post){
    
    let commentHolder = document.createElement('div');
    commentHolder.setAttribute("id", "coho");
    commentHolder.setAttribute("class", "col s12 left-align");
    let userH = document.createElement('h6');
    let userE = document.createTextNode(commentData.email + " commented: ");
    userH.appendChild(userE);
    let titleH = document.createElement('h6');
    let titleTxt = document.createTextNode(commentData.name);
    titleH.appendChild(titleTxt);
    let bodySpan = document.createElement('span');
    let bodyTxt = document.createTextNode(commentData.body);
    bodySpan.appendChild(bodyTxt);
    commentHolder.appendChild(userH);
    commentHolder.appendChild(titleH);
    commentHolder.appendChild(bodySpan);
    post.appendChild(commentHolder);
}

function hideComments(post){
    for(i=0; i < post.childNodes.length; i++){
        if(post.childNodes[i].id == "coho"){
            post.childNodes[i].style.display = "none";
        }
    }
}

function commsCheck(post){
    for(i=0; i<post.childNodes.length; i++){
        if(post.childNodes[i].id == "coho"){
            return true;
        }
    }
    return false;
}

function randomNum(max){
    return Math.floor(Math.random() * max + 1);
}