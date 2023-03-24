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

const removeItemBtn = document.querySelectorAll('.remove-btn');
const addItemBtn = document.querySelectorAll('.add-btn');


function updateCart(operator, productId) {
    let itemQuantity = document.getElementById('item-quantity-'+productId).innerText;
    console.log("item qty BEFORE clicking the + button"+itemQuantity)

    if(operator === "+") {
        itemQuantity++;
        console.log("item qty AFTER clicking the + button"+itemQuantity)
        return itemQuantity
    } else if (operator === "-"){
        itemQuantity--;
       return itemQuantity--
    }
}

const handleSubmitRemoveItem = async (e) => {

    let itemPrice = document.getElementById('item-price').innerText;
    productId = e.target.value;

    const slicedPrice = itemPrice.slice(1);
    const priceFormatted = parseFloat(slicedPrice.replace(",", ""));

    let qty = updateCart("-", productId);
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
        //remove div
        document.getElementById(responseArr[3]).style.display = "none";

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

    document.getElementById('item-quantity-'+productId).innerText = responseArr[0];
    document.getElementById('itemCount-'+productId).innerText = responseArr[0];
    const subtotal = parseFloat(responseArr[1])
    document.getElementById('order-subtotal-'+productId).innerText = formatter.format(subtotal);    const total = parseFloat(responseArr[3])
    console.log(total)
    document.getElementById('order-total').innerText = formatter.format(total);

}
const handleSubmitAddItem = async (e) => {
    productId = e.target.value;
    let itemPrice = document.getElementById('item-price').innerText;
    console.log(document.getElementById('product-name').innerText)


    const slicedPrice = itemPrice.slice(1);
    const priceFormatted = parseFloat(slicedPrice.replace(",", ""));

    let qty = updateCart("+", productId);
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


    document.getElementById('item-quantity-'+productId).innerText = responseArr[0];
    document.getElementById('itemCount-'+productId).innerText = responseArr[0];
    const subtotal = parseFloat(responseArr[1])
    document.getElementById('order-subtotal-'+productId).innerText = formatter.format(subtotal);
    const total = parseFloat(responseArr[3])
    document.getElementById('order-total').innerText = formatter.format(total);

}

const checkoutItemsBtn = document.getElementById('checkout-btn');

if(removeItemBtn != null) {
removeItemBtn.forEach((button) => {
    button.addEventListener("click", handleSubmitRemoveItem)})

}
if(addItemBtn != null) {
addItemBtn.forEach((button) => {
    console.log(button)
    button.addEventListener("click", handleSubmitAddItem)})
}