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
    let responseArr = await response.json()
    console.log(responseArr)


    document.getElementById('item-quantity').innerText = responseArr[0];
    document.getElementById('itemCount').innerText = responseArr[0];
    document.getElementById('item-subtotal').innerText = responseArr[1];
    document.getElementById('order-total').innerText = responseArr[2];

}


const checkoutItemsBtn = document.getElementById('checkout-btn');

const handleCheckoutItems = async (e) => {
    console.log("CHECKOUT BUTTON ")
    const urlOrders = 'http://localhost:8080/orders/cart'

    console.log("ORDER ID CHECKOUT PAGE: "+orderId)
//    const response = await fetch(`${urlOrders}/checkout?id=${orderId}`) {
//  method: 'GET'
//})
//  .then(response => response.json())
//  .then(data => {
//    console.log(data);
//  });

//    document.getElementById('summary-product-price').innerText = document.getElementById('item-price').innerText;;
//    document.getElementById('summary-product-quantity').innerText = responseArr[0];
//    document.getElementById('summary-product-subtotal').innerText = responseArr[1];
//    document.getElementById('summary-product-total').innerText = responseArr[2] ;



}

checkoutItemsBtn.addEventListener("click", handleCheckoutItems);
removeItemBtn.addEventListener("click", handleSubmitRemoveItem);
addItemBtn.addEventListener("click", handleSubmitAddItem);



