

function checkInputs() {
    let result = false;
    const form = document.getElementById('signup-id');
    const name = document.getElementById('name');
    const surname = document.getElementById('surname');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const password1 = document.getElementById('password1');
    const card = document.getElementById('card');
    const address = document.getElementById('address');
    const phone = document.getElementById('phone');
    const date = document.getElementById('date');
    const btn = document.getElementById('submit-btn');

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

     var count = 0;

    if (nameValue === '') {
        setErrorFor(name, 'Fill in the required field');
    } else if (!/(^[\sa-zA-Z,.'-]+|^[\sа-яА-Я,.'-]+)$/.test(nameValue)) {
        setErrorFor(name, 'Enter a valid name');
    } else {
        setSuccessFor(name);
        count++;
    }

    if (surnameValue === '')  {
        setErrorFor(surname, 'Fill in the required field');
    } else if (!/(^[\sa-zA-Z,.'-]+|^[\sа-яА-Я,.'-]+)$/.test(surnameValue)) {
        setErrorFor(surname, 'Enter a valid surname');
    } else {
        setSuccessFor(surname);
        count++;
    }

    if (emailValue === '')  {
        setErrorFor(email, 'Fill in the required field');
    } else if (!isEmail(emailValue)) {
        setErrorFor(email, 'Email is not valid');
    } else {
        setSuccessFor(email);
        count++;
    }

    if (addressValue === '')  {
        setErrorFor(address, 'Fill in the required field');
    } else {
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