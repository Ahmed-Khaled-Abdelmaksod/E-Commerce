phoneError = document.getElementById("phone-error");
dobError = document.getElementById("dob-error");
emailError = document.getElementById("email-error");
passwordError = document.getElementById("password-error");
matchError = document.getElementById("match-error");
nameError = document.getElementById("name-error");

emailField = document.getElementById("email");
confirmPasswordField = document.getElementById("confirm_password");
passwordField = document.getElementById("password");
phoneField = document.getElementById("phone");
dobField = document.getElementById("dob")
nameField = document.getElementById("name")

const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const phoneRegex = /^(\+20|0)?1[0125]\d{8}$/;
const nameRegex = /^[A-Za-z\s]+$/;

emailField.addEventListener("blur", () => {
    let email = emailField.value.trim();
    if(email === "") return;
    if(emailRegex.test(email)){
        fetch(`../auth/check-email?email=` + encodeURIComponent(email)).
        then(response => {
            if(!response.ok){
                throw new Error("Network response was not ok");
            }
            return response.json();
        }).then(date => {
            if(date.available) {
                emailField.classList.add('is-valid');
                emailField.classList.remove('is-invalid');
                emailError.classList.add('d-none');
            }else {
                emailField.classList.add('is-invalid');
                emailField.classList.remove('is-valid');
                emailError.textContent = "This email is already registered.";
                emailError.classList.remove('d-none');
            }
        })
    }else {
        emailField.classList.add('is-invalid');
        emailField.classList.remove('is-valid');
        emailError.textContent = "email must match email format (example@example.com)";
        emailError.classList.remove('d-none');
    }

})
function validate() {
    
}

confirmPasswordField.addEventListener('input',() => {
    if(passwordField.value !== confirmPasswordField.value) {
        matchError.classList.remove('d-none');
        confirmPasswordField.classList.add('is-invalid');
    }else {
        matchError.classList.add('d-none');
        confirmPasswordField.classList.remove('is-invalid');
        confirmPasswordField.classList.add('is-valid');
    }
})

phoneField.addEventListener('blur',() => {
    if(phoneRegex.test(phoneField.value)) {
        phoneField.classList.add('is-valid');
        phoneField.classList.remove('is-invalid');
        phoneError.classList.add('d-none');

    } else {
        phoneField.classList.add('is-invalid');
        phoneField.classList.remove('is-valid');
        phoneError.classList.remove('d-none');
    }
})


nameField.addEventListener('blur', function() {
    const name = nameField.value.trim();

    if (name === "") {
        nameField.classList.remove('is-valid', 'is-invalid');
    } else if (nameRegex.test(name)) {
        showSuccess(nameField,nameError);
    } else {
        showError(nameField,nameError,"Name must contain only characters");
    }
});
dobField.addEventListener('change', function() {
    const birthDate = new Date(this.value);
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    if (birthDate > today) {
        showError(dobField, dobError, "Date cannot be in the future.");
    } else if (age < 13) {
        showError(dobField, dobError, "You must be at least 13 years old.");
    } else {
        showSuccess(dobField, dobError);
    }
});

function showError(input, label, message) {
    input.classList.add('is-invalid');
    input.classList.remove('is-valid');
    label.textContent = message;
    label.classList.remove('d-none');
}

function showSuccess(input, label) {
    input.classList.remove('is-invalid');
    input.classList.add('is-valid');
    label.classList.add('d-none');
}