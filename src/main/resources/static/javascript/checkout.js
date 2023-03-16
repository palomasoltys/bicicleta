  const productPrices = document.querySelectorAll(".item-product-price");
  const formatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD"
  });
  productPrices.forEach(function(price) {
    price.innerText = formatter.format(parseFloat(price.innerText));
    console.log(price.innerText)
  });


const baseUrl = 'http://localhost:8080/products/cart'

const headers = {
    'Content-Type':'application/json'
}

const removeItemBtn = document.getElementById('remove-item-btn');
const addItemBtn = document.getElementById('add-item-btn');


function updateCart(operator) {
    let itemQuantity = document.getElementById('item-quantity').innerText;

    if(operator === "+") {
    itemQuantity++;
       return itemQuantity
    } else if (operator === "-"){
    itemQuantity--;
       return itemQuantity--
    }
}

const handleSubmitRemoveItem = async (e) => {

    let itemPrice = document.getElementById('item-price').innerText;
    const productId = document.getElementById('product-id').innerText;

    const slicedPrice = itemPrice.slice(1);
    const priceFormatted = parseFloat(slicedPrice.replace(",", ""));

    let qty = updateCart("-");
    let obj = {
    quantity: qty,
    price: itemPrice,
    productId: productId
    }

    const orderId = document.getElementById('order-id').innerText;


    const response = await fetch(`${baseUrl}/update-cart/${orderId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
    const responseArr = await response.json()
    console.log(responseArr)

    //if user deletes product from the cart, display a message about empty cart
    if(responseArr[0] === "0") {
    console.log("0 quantity")
        //remove div
        document.getElementById('cart-summary').style.display = "none";

        //display: shopping cart is empty
        const emptyCartDiv = document.createElement("div");
        const emptyCartP = document.createElement("p");
        emptyCartP.classList.add('empty-cart-p')
        emptyCartP.classList.add('pb-5')
        const paragraphText = document.createTextNode("Your cart is empty. Let's shop?");

        emptyCartP.appendChild(paragraphText);
        emptyCartDiv.appendChild(emptyCartP);
        document.getElementById('cart-container').appendChild(emptyCartDiv);
    }

    document.getElementById('item-quantity').innerText = responseArr[0];
    document.getElementById('itemCount').innerText = responseArr[0];
    document.getElementById('item-subtotal').innerText = responseArr[1];
    const total = parseFloat(responseArr[2])
    console.log(total)
    document.getElementById('order-total').innerText = formatter.format(total);

}
const handleSubmitAddItem = async (e) => {
    let itemPrice = document.getElementById('item-price').innerText;
    const productId = document.getElementById('product-id').innerText;

    const slicedPrice = itemPrice.slice(1);
    const priceFormatted = parseFloat(slicedPrice.replace(",", ""));

    let qty = updateCart("+");
    let obj = {
    quantity: qty,
    price: itemPrice,
    productId: productId
    }

    const orderId = document.getElementById('order-id').innerText;


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
    const total = parseFloat(responseArr[2])
    console.log(total)
    document.getElementById('order-total').innerText = formatter.format(total);

}

const checkoutItemsBtn = document.getElementById('checkout-btn');

if(removeItemBtn != null) {
removeItemBtn.addEventListener("click", handleSubmitRemoveItem);
}
if(addItemBtn != null) {
addItemBtn.addEventListener("click", handleSubmitAddItem);
}