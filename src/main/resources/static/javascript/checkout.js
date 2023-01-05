let itemQuantity = document.getElementById('item-quantity').innerText;
const itemPrice = document.getElementById('item-price').innerText;
const itemSubTotal = document.getElementById('item-subtotal').innerText;
const productName = document.getElementById('product-name').innerText;
const productDescription = document.getElementById('product-description').innerText;
const orderId = document.getElementById('order-id').innerText;
let url = window.location.href;
let productId = url.substring(url.lastIndexOf('/') + 1);

const baseUrl = 'http://localhost:8080/products/cart'

const headers = {
    'Content-Type':'application/json'
}

const removeItemBtn = document.getElementById('remove-item-btn');


const addItemBtn = document.getElementById('add-item-btn');


function updateCart(operator) {
    if(operator === "+") {
        itemQuantity++
    } else if (operator === "-"){
        itemQuantity--
    }

    let obj = {
    id: orderId,
    quantity: itemQuantity
    }
//
//    const response = await fetch(`${baseUrl}/update-cart/${productId}`, {
//    method: "POST",
//    body: JSON.stringify(obj),
//    headers: headers
//    })
//    console.log(response)
}

const checkoutItemsBtn = document.getElementById('checkout-btn');

//checkoutItemsBtn.addEventListener('click', () => {
//})

removeItemBtn.addEventListener("click", () => { updateCart("-") });
addItemBtn.addEventListener("click", () => { updateCart("+") });



