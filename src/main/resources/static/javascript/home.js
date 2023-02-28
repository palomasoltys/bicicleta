  const priceElements = document.querySelectorAll(".p-price");
  priceElements.forEach(priceElement => {
    let price = parseFloat(priceElement.innerText);
    const formattedPrice = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(price);
    priceElement.innerText = formattedPrice;
  });