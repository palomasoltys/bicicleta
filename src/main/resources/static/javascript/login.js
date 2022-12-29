let loginForm = document.getElementById('login-form')
let loginEmail = document.getElementById('login-email')
let loginPassword = document.getElementById('login-password')

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/users'

const handleSubmit = async (e) =>{
    e.preventDefault()

    let bodyObj = {
        email: loginEmail.value,
        password: loginPassword.value
    }
    console.log(bodyObj);

    const response = await fetch(`${baseUrl}/login-form`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
    console.log(response)
        .catch(err => console.error(err.message))

    const responseArr = await response.json()

    if (response.status === 200){
        document.cookie = `userId=${responseArr[1]}`
        window.location.replace(responseArr[0])
    }
}

loginForm.addEventListener("submit", handleSubmit)
