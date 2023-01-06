let itemQuantity = document.getElementById('item-quantity').innerText;
let itemPrice = document.getElementById('item-price').innerText;
let itemSubTotal = document.getElementById('item-subtotal').innerText;
const productName = document.getElementById('product-name').innerText;
const productId = document.getElementById('product-id').innerText;
const productDescription = document.getElementById('product-description').innerText;
const orderId = document.getElementById('order-id').innerText;

const baseUrl = 'http://localhost:8080/products/cart'

const headers = {
    'Content-Type':'application/json'
}

const removeItemBtn = document.getElementById('remove-item-btn');
const addItemBtn = document.getElementById('add-item-btn');


function updateCart(operator) {
    if(operator === "+") {
    itemQuantity++;
       return itemQuantity
    } else if (operator === "-"){
    itemQuantity--;
       return itemQuantity--
    }
}

const handleSubmitRemoveItem = async (e) => {
    let qty = updateCart("-");
    let obj = {
    quantity: qty,
    price: itemPrice,
    productId: productId
    }

    const response = await fetch(`${baseUrl}/update-cart/${orderId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
    const responseArr = await response.json()
    console.log(responseArr)


    document.getElementById('item-quantity').innerText = responseArr[0];
    document.getElementById('itemCount').innerText = responseArr[0];
    document.getElementById('item-subtotal').innerText = responseArr[1];
    document.getElementById('order-total').innerText = responseArr[2];

}

const handleSubmitAddItem = async (e) => {
    let qty = updateCart("+");
    let obj = {
    quantity: qty,
    price: itemPrice,
    productId: productId
    }

    const response = await fetch(`${baseUrl}/update-cart/${orderId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
    const responseArr = await response.json()
    console.log(responseArr)


    document.getElementById('item-quantity').innerText = responseArr[0];
    document.getElementById('itemCount').innerText = responseArr[0];
    document.getElementById('item-subtotal').innerText = responseArr[1];
    document.getElementById('order-total').innerText = responseArr[2];

}


const checkoutItemsBtn = document.getElementById('checkout-btn');

const handleCheckoutItems = async (e) => {


}

removeItemBtn.addEventListener("click", handleSubmitRemoveItem);
addItemBtn.addEventListener("click", handleSubmitAddItem);



