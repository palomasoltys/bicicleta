let url = window.location.href;
let productId = url.substring(url.lastIndexOf('/') + 1);

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
        productId: productId,
        productName: productName.innerText,
        productDescription: productDescription.innerText,
        productImgUrl: productImgUrl.src,
        productPrice: productPrice.innerText,
        productQtyToTheCart: productQtyToTheCart.value,

    }
    console.log(bodyObj);

//    const response = await fetch(`${baseUrl}/add-to-the-cart`, {
//        method: "POST",
//        body: JSON.stringify(bodyObj),
//        headers: headers
//    })
//    console.log(response)
//        .catch(err => console.error(err.message))
//    console.log(response)
//
//    const responseArr = await response.json()
//    console.log(responseArr)
//
//    if (response.status === 200){
//        document.cookie = `userId=${responseArr[1]}`
//        console.log(document.cookie)
//        window.location.replace(responseArr[0])
//        console.log(document.cookie)
//
//    }
}

addToTheCartForm.addEventListener("submit", handleSubmit)




