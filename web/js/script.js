function tableSearch(id) {
    var phrase = document.getElementById('search-text');
    var table = document.getElementById(id);
    var regPhrase = new RegExp(phrase.value, 'i');
    var flag = false;
    for (var i = 1; i < table.rows.length; i++) {
        flag = false;
        for (var j = table.rows[i].cells.length - 1; j >= 0; j--) {
            flag = regPhrase.test(table.rows[i].cells[j].innerHTML);
            if (flag) break;
        }
        if (flag) {
            table.rows[i].style.display = "";
        } else {
            table.rows[i].style.display = "none";
        }

    }
}

function openForm(id) {
    document.getElementById(id).style.display = "block";
    document.getElementById('login-background').style.display = "block";
}

function closeForm(id) {
    document.getElementById(id).style.display = "none";
    document.getElementById('login-background').style.display = "none";
}

function heartClicked() {
    const heart = document.getElementById('add-to-wl');
    if (heart.classList.contains('clicked')) {
        heart.classList.remove('clicked');
    } else {
        heart.classList.add('clicked');
    }
}

function editProfile() {
    const inputs = document.getElementsByClassName('to-be-amended');
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].style.display = 'block';
    }
}

function selectQuantity(id) {
    const option = document.getElementById('to-be-selected');
    option.classList.add('selected');
}