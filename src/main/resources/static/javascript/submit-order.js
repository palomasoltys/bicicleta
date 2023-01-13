let url = window.location.href;
let orderId = url.substring(url.lastIndexOf('/') + 1);
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

    const addressObj = {
        address: address,
        city: city,
        state: state,
        zipcode: zipcode,
        country: country
    }

    const responseAddress = await fetch(`${urlSubmit}/submit/${orderId}`, {
        method: "POST",
        body: JSON.stringify(addressObj),
        headers: header
    })

    let responseAddressArr = await responseAddress.json()
    console.log(responseAddressArr)

    const responsePayment = await fetch(`${urlSubmit}/submit/payment/${orderId}`, {
        method: "POST",
        headers: header
    })
    let responsePaymentArr = await responsePayment.json()
    console.log(responsePaymentArr)
    window.location.replace("http://localhost:8080/orders/thank-you")

}
submitOrderBtn.addEventListener("click", handleSubmitOrder);


