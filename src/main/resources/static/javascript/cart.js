let url = window.location.href;
let id = url.substring(url.lastIndexOf('/') + 1);

const addToTheCartForm = document.getElementById('add-to-cart-form')
const productName = document.getElementById('product-name')
const productDescription = document.getElementById('product-description')
const productImgUrl = document.getElementById('product-url')
const productPrice = document.getElementById('product-price')
const productQtyToTheCart = document.getElementById('product-quantity')

// convert price to $#,###.##
const priceElement = document.querySelector(".p-price");
let price = parseFloat(priceElement.innerText);
const formattedPrice = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(price);
console.log(formattedPrice)
priceElement.innerText = formattedPrice;

const slicedPrice = productPrice.innerText.slice(1);
const priceFormatted = parseFloat(slicedPrice.replace(",", ""));

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/products/cart'

const handleSubmit = async (e) => {
    e.preventDefault()
    const bodyObj = {
        id: id,
        name: productName.innerText,
        description: productDescription.innerText,
        imgUrl: productImgUrl.src,
        price: priceFormatted
    }
    console.log(bodyObj);
    const response = await fetch(`${baseUrl}/add-to-the-cart/${id}?quantity=${productQtyToTheCart.value}`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
    console.log(response)

    const responseArr = await response.json()
    console.log(responseArr)

    if (response.status === 200){
    console.log("WORKED")

    window.alert(`${productName.innerText} was added to your cart.`)
    document.getElementById('your-cart').innerText = `Your Cart[${responseArr[0]}]`


    } else if(response.status === 400) {
    console.log(responseArr[0])
    window.alert(`${responseArr[0]}`)
    console.log("BAD REQUEST")
    }
}

addToTheCartForm.addEventListener("submit", handleSubmit)




