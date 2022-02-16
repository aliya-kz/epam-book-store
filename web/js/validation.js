function checkBook() {
    const title = document.getElementById('title');
    const publisher = document.getElementById('publisher');
    const isbn = document.getElementById('isbn');
    const price = document.getElementById('price');
    const description = document.getElementById('description');
    const quantity = document.getElementById('quantity');

    if (title !== null && !checkItem(title,200)) {
        return false;
    }
    if (publisher !== null && !checkItem(publisher,200)){
        return false;
    }
    if (description !== null && !checkItem(description,1500)){
        return false;
    }
    if (isbn !== null && !checkISBN(isbn)) {
        return false;
    }
    if (quantity !== null && !checkQty(quantity)) {
        return false;
    }
    if (price !== null && !checkPrice(price)) {
        return false;
    }
    return true;
}


function checkAuthorTranslation() {
    const id = document.getElementById('transl-id');
    const name = document.getElementById('transl-name');
    const surname = document.getElementById('transl-surname');
    const bio = document.getElementById('transl-biography');

    if (id !== null && !checkQty(id)) {
        return false;
    }
    if (name !== null && !checkName(name)) {
        return false;
    }
    if (surname !== null && !checkSurname(surname)) {
        return false;
    }
    if (bio !== null && !checkItem(bio, 1500)) {
        return false;
    }
    return true;
}

function checkAuthor() {
    const name = document.getElementById('name');
    const surname = document.getElementById('surname');
    const bio = document.getElementById('biography');

    if (name !== null && !checkName(name)) {
        return false;
    }
    if (surname !== null && !checkSurname(surname)) {
        return false;
    }
    if (bio !== null && !checkItem(bio, 1500)) {
        return false;
    }
    return true;
}

function editAuthor() {
    const name = document.getElementById('name');
    const surname = document.getElementById('surname');
    const bio = document.getElementById('biography');

    if (name !== null && !checkName(name)) {
        return false;
    }
    if (surname !== null && !checkSurname(surname)) {
        return false;
    }
    if (bio !== null && !checkItem(bio, 1500)) {
        return false;
    }
    return true;
}

function checkCategory () {
    const item = document.getElementById('category');
    const itemValue = item.value.trim();
    const id = document.getElementById('id');

    if (id !== null && !checkQty(id)) {
        return false;
    }
    if (itemValue === '') {
        return true;
    } else if (!/[a-zA-Z\s\.\,\']+|[а-яА-Я\s\.\,\']+$/.test(itemValue)) {
        setErrorFor(item, 'Enter a valid category name');
        return false;
    } else if (itemValue.length > 50) {
        setErrorFor(item, 'Category name cannot be longer than 50 characters');
        return false;
    }else {
        setSuccessFor(item);
        return true;
    }
}

function checkItem (item, length) {
    const itemValue = item.value.trim();
    if (itemValue === '') {
        return true;
    } else if (itemValue.length > length) {
        setErrorFor(item,  item.id + ' cannot be longer than ' + length + ' characters');
        return false;
    } else {
        setSuccessFor(item);
        return true;
    }
}

function checkISBN (item) {
    const itemValue = item.value.trim();
    if (itemValue === '') {
        return true;
    } else if (!/\d{10}$/.test(itemValue) || !/\d{13}$/.test(itemValue)) {
        setErrorFor(item, 'Please enter 10 or 13 digits long number. No other characters are acceptable.');
        return false;
    }  else {
        setSuccessFor(item);
        return true;
    }
}

function checkQty (item) {
    const itemValue = item.value.trim();
    const itemNumber = Number(itemValue);
    if (itemValue === '') {
        return true;
    } else
        if (!Number.isInteger(itemNumber) || itemNumber < 0) {
        setErrorFor(item, 'Please enter a valid ' + item.id);
        return false;
    }  else {
        setSuccessFor(item);
        return true;
    }
}

function checkPrice (item) {
    const itemValue = item.value.trim();
    const itemNumber = Number(itemValue);
    if (itemValue === '') {
        return true;
    } else
    if (isNaN(itemNumber) || itemValue < 0) {
        setErrorFor(item, 'Please enter a valid price.');
        return false;
    }  else {
        setSuccessFor(item);
        return true;
    }
}

function editProfile() {
    const inputs = document.getElementsByClassName('to-be-amended');
    const saveBtn = document.getElementById('save-profile');
    const editBtn = document.getElementById('edit-profile-btn');

    editBtn.style.display = 'none';
    saveBtn.style.display = 'block';

    for (let i = 0; i < inputs.length; i++) {
        inputs[i].style.display = 'block';
    }

}

function checkPassword () {
    let result = false;
    const password = document.getElementById('password');
    const newPassword = document.getElementById('new-password');
    const newPassword1 = document.getElementById('new-password1');

    const passValue = password.value.trim();
    const newPassValue = newPassword.value.trim();
    const newPassValue1 = newPassword1.value.trim();

    let count = 0;

    if (passValue === '')  {
        setErrorFor(password, 'Fill in the required field');
    } else if (!/[a-zA-Z0-9]+$/.test(passValue)) {
        setErrorFor(password, 'Only latin characters and digits are acceptable');
    } else {
        setSuccessFor(password);
        count++;
    }

    if (newPassValue === '')  {
        setErrorFor(newPassword, 'Fill in the required field');
    } else if (!/[a-zA-Z0-9]+$/.test(newPassValue)) {
        setErrorFor(newPassword, 'Only latin characters and digits are acceptable');
    } else {
        setSuccessFor(newPassword);
        count++;
    }

    if (newPassValue1 === '')  {
        setErrorFor(newPassword1, 'Fill in the required field');
    } else if (newPassValue !== newPassValue1) {
        setErrorFor(newPassword1, 'Password does not match')
    }  else {
        setSuccessFor(newPassValue1);
        count++;
    }
    if (count === 3) {
        result = true;
    }
    return result;
}

function checkCard () {
    let result = false;
    const card = document.getElementById('prof-card');
    const cardValue = card.value.replace(/[\s\-]/g,'');

    if (cardValue === '')  {
        setErrorFor(card, 'Fill in the required field');
        return false;
    } else if (!/\d{13,16}$/.test(cardValue) || cardValue.length < 13 || cardValue.length > 16) {
        setErrorFor(card, 'Enter a valid card number');
        return false;
    } else {
        setSuccessFor(card);
        result = true;
    }
    return result;
}

function checkName (name) {
    const nameValue = name.value.trim();
    if (nameValue === '') {
       return true;
    } else if (!/(^[\sa-zA-Z,.'-]+|^[\sа-яА-Я,.'-]+)$/.test(nameValue)) {
        setErrorFor(name, 'Enter a valid name');
        return false;
    } else if (nameValue.length > 50) {
        setErrorFor(name, name.id + ' cannot be longer than 50 characters');
        return false;
    } else {
        setSuccessFor(name);
        return true;
    }
}

function checkSurname (surname) {
    const surnameValue = surname.value.trim();
    if (surnameValue === '')  {
        return true;
    } else if (!/(^[\sa-zA-Z,.'-]+|^[\sа-яА-Я,.'-]+)$/.test(surnameValue)) {
        setErrorFor(surname, 'Enter a valid surname');
        return false;
    } else if (surnameValue.length > 50) {
        setErrorFor(surname, 'Surname cannot be longer than 50 characters');
        return false;
    } else {
        setSuccessFor(surname);
        return true;
    }
}

function checkPhone(phone) {
    const phoneValue = phone.value.replace(/[\s\-\\(\\)]/g,'');
    if (phoneValue === '') {
        return true;
    } else if (!/\d{10,15}$/.test(phoneValue) || phoneValue.length < 10 || phoneValue.length > 15) {
        setErrorFor(phone, 'A phone number can contain from 10 to 15 digits and special character "+"');
        return false;
    } else {
        setSuccessFor(phone);
        return true;
    }
}

function checkPersonal(){
    const name = document.getElementById('name');
    const surname = document.getElementById('surname');
    const phone = document.getElementById('phone');

    if (name !== null && checkName(name) === false) {
      return false;
    }
    if (surname !== null && checkSurname(surname) === false) {
        return false;
    }
    if (phone !== null && checkPhone(phone) ===false) {
        return false;
    }
    return true;
}

function checkInputs() {
    let result = false;
    const name = document.getElementById('name');
    const surname = document.getElementById('surname');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const password1 = document.getElementById('password1');
    const card = document.getElementById('card');
    const address = document.getElementById('address');
    const phone = document.getElementById('phone');
    const date = document.getElementById('date');

    const nameValue = name.value.trim();
    const surnameValue = surname.value.trim();
    const emailValue = email.value.trim();
    const addressValue = address.value.trim();
    const phoneValue = phone.value.replace(/[\s\-\\(\\)]/g,'');
    console.log(phoneValue);
    const dateValue = date.value;
    const cardValue = card.value.replace(/[\s\-]/g,'');
    console.log(cardValue);
    const passValue = password.value.trim();
    const passValue1 = password1.value.trim();

     let count = 0;

    if (nameValue === '') {
        setErrorFor(name, 'Fill in the required field');
    } else if (!/(^[\sa-zA-Z,.'-]+|^[\sа-яА-Я,.'-]+)$/.test(nameValue)) {
        setErrorFor(name, 'Enter a valid name');
    } else if (nameValue.length > 100) {
        setErrorFor(name, 'Name cannot be longer than 100 characters');
    } else {
        setSuccessFor(name);
        count++;
    }

    if (surnameValue === '')  {
        setErrorFor(surname, 'Fill in the required field');
    } else if (!/(^[\sa-zA-Z,.'-]+|^[\sа-яА-Я,.'-]+)$/.test(surnameValue)) {
        setErrorFor(surname, 'Enter a valid surname');
    } else if (surnameValue.length > 100) {
        setErrorFor(name, 'Surname cannot be longer than 100 characters');
    } else {
        setSuccessFor(surname);
        count++;
    }

    if (emailValue === '')  {
        setErrorFor(email, 'Fill in the required field');
    } else if (!isEmail(emailValue)) {
        setErrorFor(email, 'Email is not valid');
    } else if (emailValue.length > 100) {
        setErrorFor(email, 'Email cannot be longer than 100 characters');
    } else {
        setSuccessFor(email);
        count++;
    }

    if (addressValue === '')  {
        setErrorFor(address, 'Fill in the required field');
    } else if (addressValue.length > 100) {
        setErrorFor(email, 'Address must be not longer than 100 characters');
    }  else {
        setSuccessFor(address);
        count++;
    }

    if (cardValue === '')  {
        setErrorFor(card, 'Fill in the required field');
    } else if (!/\d{13,16}$/.test(cardValue) || cardValue.length < 13 || cardValue.length > 16) {
        setErrorFor(card, 'Enter a valid card number');
    } else {
        setSuccessFor(card);
        count++;
    }

    if (phoneValue === '') {
        setErrorFor(phone, 'Fill in the required field');
    } else if (!/\d{10,15}$/.test(phoneValue) || phoneValue.length < 10 || phoneValue.length > 15) {
        setErrorFor(phone, 'A phone number can contain from 10 to 15 digits and special character "+"');
    } else {
        setSuccessFor(phone);
        count++;
    }

    let now = new Date();
    if (dateValue === '') {
        setErrorFor(date, 'Please fill in your date of birth.');
    } else if ((now - new Date(dateValue)) < (24 * 3600 * 365.25 * 1000 * 18)) {
            setErrorFor(date, 'The user must be at least 18 y.o.');
        } else {
            setSuccessFor(date);
        count++;
        }

    if (passValue === '')  {
        setErrorFor(password, 'Fill in the required field');
    } else if (!/[a-zA-Z0-9]+$/.test(passValue)) {
        setErrorFor(password, 'Only latin characters and digits are acceptable');
    } else if (passValue.length < 4 || passValue.length > 12) {
        setErrorFor(password, 'Password must be at least 4 and at most 12 characters long');
    } else {
        setSuccessFor(password);
        count++;
    }

    if (passValue1 === '')  {
        setErrorFor(password1, 'Fill in the required field');
    } else if (passValue1 !== passValue) {
        setErrorFor(password1, 'Password does not match')
    }  else {
        setSuccessFor(password1);
        count++;
    }

    if (count === 9) {
        result = true;
    }

    return result;
}

function setErrorFor(input, message) {
    const formControl = input.parentElement;
    const small = formControl.querySelector('small');
    small.innerText = message;
    formControl.className = 'form-control error';
}

function setSuccessFor(input) {
    const formControl = input.parentElement;
    formControl.className = 'form-control success';
}

function isEmail(email) {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email);
}