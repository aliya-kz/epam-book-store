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
    const html = document.querySelector('html');
    const body = document.querySelector('body');
    html.classList.add('unscrollable');
    body.classList.add('unscrollable');
}

function closeForm(id) {
    document.getElementById(id).style.display = "none";
    document.getElementById('login-background').style.display = "none";
    const html = document.querySelector('html');
    const body = document.querySelector('body');
    html.classList.remove('unscrollable');
    body.classList.remove('unscrollable');
}

function showEl(id) {
    document.getElementById(id).style.display = "block";
   }

function hideEl(id) {
    document.getElementById(id).style.display = "none";
}

function selectStyle(className, id) {
    const items = document.getElementsByClassName(className);
    const selectedItem = document.getElementById(id);
    selectedItem.classList.add('selected');
    for (let i = 0; i < items.length; i++) {
        if (items[i] !== selectedItem) {
            items[i].classList.remove('selected');
        }
    }
}