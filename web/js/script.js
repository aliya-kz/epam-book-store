function tableSearch(id) {
    let phrase = document.getElementById('search-text');
    let table = document.getElementById(id);
    let regPhrase = new RegExp(phrase.value, 'i');
    let flag = false;
    for (let i = 1; i < table.rows.length; i++) {
        flag = false;
        for (let j = table.rows[i].cells.length - 1; j >= 0; j--) {
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

function selectStyle(id) {
    const selectedItem = document.getElementById(id);
    if (selectedItem.classList.contains('selected')) {
        selectedItem.classList.remove('selected');
    } else {
        selectedItem.classList.add('selected');
    }
}