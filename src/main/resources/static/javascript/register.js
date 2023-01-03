let registerForm = document.getElementById('register-form')
let registerName = document.getElementById('register-name')
let registerEmail = document.getElementById('register-email')
let registerPassword = document.getElementById('register-password')

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/api/users'

const handleSubmit = async (e) =>{
    e.preventDefault()
console.log("TESTE")

    let bodyObj = {
        name: registerName.value,
        email: registerEmail.value,
        password: registerPassword.value
    }
    console.log(bodyObj);

    const response = await fetch(`${baseUrl}/register-form`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    console.log(response)

    const responseArr = await response.json()
    console.log(responseArr)

    if (response.status === 200){
//        document.cookie = `userId=${responseArr[1]}`
//        console.log(document.cookie)
        window.location.replace("/")
//        console.log(document.cookie)

    }
}

registerForm.addEventListener("submit", handleSubmit)
