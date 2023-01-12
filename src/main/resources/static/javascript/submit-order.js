let url = window.location.href;
let orderId = url.substring(url.lastIndexOf('/') + 1);
console.log("CHECK")
const submitOrderBtn = document.getElementById('submit-order-btn');
const address = document.getElementById('address').innerText;
const city = document.getElementById('city').innerText;
const state = document.getElementById('state').value;
const zipcode = document.getElementById('zipcode').innerText;
const country = document.getElementById('country').value;

const header = {
    'Content-Type':'application/json'
}

const urlSubmit = 'http://localhost:8080/orders/cart/checkout'
console.log(submitOrderBtn);
const handleSubmitOrder = async (e) => {
    e.preventDefault()
console.log("TESTE")
    const addressObj = {
        address: address,
        city: city,
        state: state,
        zipcode: zipcode,
        country: country
    }

    const response = await fetch(`${urlSubmit}/submit/${orderId}`, {
        method: "POST",
        body: JSON.stringify(addressObj),
        headers: header
    })
    let responseArr = await response.json()
    console.log(responseArr)
}
submitOrderBtn.addEventListener("click", handleSubmitOrder);


