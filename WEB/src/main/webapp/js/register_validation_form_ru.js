function validateForm() {
	var result = true;
	var FILL_FIELD = " *Заполните это поле", 
	BAD_PASSPORT = " *Некорректный номер паспорта", 
	BAD_EMAIL = " *Некорректный адрес e-mail", 
	BAD_LOGIN = " *Некорректный логин", 
	WEAK_PASSWORD = " *Слабый пароль", 
	PWD_NOT_EQUAL = " *Пароли не совпадают";

	var errFname = document.getElementById("err-fname"), errLname = document
			.getElementById("err-lname"), errPassport = document
			.getElementById("err-passp"), errEmail = document
			.getElementById("err-email"), errLogin = document
			.getElementById("err-login"), errPwd1 = document
			.getElementById("err-pwd1"), errPwd2 = document
			.getElementById("err-pwd2");

	errFname.innerHTML = "";
	errLname.innerHTML = "";
	errPassport.innerHTML = "";
	errEmail.innerHTML = "";
	errLogin.innerHTML = "";
	errPwd1.innerHTML = "";
	errPwd2.innerHTML = "";

	var first_name = document.RegForm.firstname.value, 
	last_name = document.RegForm.lastname.value, 
	passport = document.RegForm.passport.value, 
	email = document.RegForm.email.value, 
	login = document.RegForm.login.value, 
	pwd1 = document.RegForm.pwd1.value, 
	pwd2 = document.RegForm.pwd2.value;

	// alert(birth_date);

	// NAME
	if (!first_name) {
		errFname.innerHTML = FILL_FIELD;
		setInvalid(document.RegForm.firstname);
		result = false;
	} else {
		setValid(document.RegForm.firstname);
	}

	if (!last_name) {
		errLname.innerHTML = FILL_FIELD;
		setInvalid(document.RegForm.lastname);
		result = false;
	} else {
		setValid(document.RegForm.lastname);
	}

	// PASSPORT
	if (!passport) {
		errPassport.innerHTML = FILL_FIELD;
		setInvalid(document.RegForm.passport);
		result = false;
	} else if (passport.search(/^([0-9]|[A-Z]){12,20}$/) !== 0) {
		errPassport.innerHTML = BAD_PASSPORT;
		setInvalid(document.RegForm.passport);
	} else {
		setValid(document.RegForm.passport);
	}


	// E-MAIL
	if (!email) {
		errEmail.innerHTML = FILL_FIELD;
		setInvalid(document.RegForm.email);
		result = false;
	}

	else if (email.search(/^[\w]+[.]?[\w]+[@][\w]+[\.][a-zA-Z]{2,4}$/) !== 0) {
		errEmail.innerHTML = BAD_EMAIL;
		setInvalid(document.RegForm.email);

	} else {
		setValid(document.RegForm.email);
	}

	// LOGIN
	if (!login) {
		errLogin.innerHTML = FILL_FIELD;
		setInvalid(document.RegForm.login);
		result = false;
	} else if (login.search(/^[a-zA-z](\w){4,}$/) !== 0) {
		errLogin.innerHTML = BAD_LOGIN;
		setInvalid(document.RegForm.login);
	} else {
		setValid(document.RegForm.login);
	}

	// PASSWORDS
	if (!pwd1) {
		errPwd1.innerHTML = FILL_FIELD;
		setInvalid(document.RegForm.pwd1);
		result = false;
	} else if (pwd1.search(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/) !== 0) {
		errPwd1.innerHTML = WEAK_PASSWORD;
		setInvalid(document.RegForm.pwd1);
		result = false;
	} else {
		setValid(document.RegForm.pwd1);
	}

	if (!pwd2) {
		errPwd2.innerHTML = FILL_FIELD;
		setInvalid(document.RegForm.pwd2);
		result = false;
	} else if (pwd1 !== pwd2) {
		errPwd2.innerHTML = PWD_NOT_EQUAL;
		setInvalid(document.RegForm.pwd2);
		result = false;
	} else {
		setValid(document.RegForm.pwd2);
	}

	/*if (result == true) {
		alert('Done!');
	}*/

	return result;
}

function setInvalid(node) {
	node
			.setAttribute(
					'style',
					'background: #fff url(images/invalid.png) no-repeat 98% center;  box-shadow: 0 0 5px #d45252; border-color: #b03535; ');
}

function setValid(node) {
	node
			.setAttribute(
					'style',
					'background: #fff url(images/valid.png) no-repeat 98% center; box-shadow: 0 0 5px #5cd053; border-color: #28921f;');
}

function getCurrentAge(date) {
	return ((new Date().getTime() - new Date(date)) / (24 * 3600 * 365.25 * 1000)) | 0;
}
