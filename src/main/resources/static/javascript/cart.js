let url = window.location.href;
let id = url.substring(url.lastIndexOf('/') + 1);

const addToTheCartForm = document.getElementById('add-to-cart-form')
const productName = document.getElementById('product-name')
const productDescription = document.getElementById('product-description')
const productImgUrl = document.getElementById('product-url')
const productPrice = document.getElementById('product-price')
const productQtyToTheCart = document.getElementById('product-quantity')


const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/products/cart'

const handleSubmit = async (e) =>{
    e.preventDefault()

    let bodyObj = {
        id: id,
        name: productName.innerText,
        description: productDescription.innerText,
        imgUrl: productImgUrl.src,
        price: productPrice.innerText,
        quantity: productQtyToTheCart.value,

    }
    console.log(bodyObj);

    const response = await fetch(`${baseUrl}/add-to-the-cart/${id}`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
    console.log(response)
//        .catch(err => console.error(err.message))
//    console.log(response)
//
    const responseArr = await response.json()
    console.log(responseArr)
//
    if (response.status === 200){
    console.log("WORKED")
//        document.cookie = `userId=${responseArr[1]}`
//        console.log(document.cookie)
//        window.location.replace(responseArr[0])
//        console.log(document.cookie)
//
    } else if(response.status === 400) {
    console.log("BAD REQUEST")
    }
}

addToTheCartForm.addEventListener("submit", handleSubmit)




