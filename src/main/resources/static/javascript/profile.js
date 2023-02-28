const dateElements = document.querySelectorAll('.profile-order-p#date-created span');

dateElements.forEach(dateElement => {
  const dateString = dateElement.textContent.trim();
  const date = new Date(dateString);
  const formattedDate = `${(date.getMonth() + 1).toString().padStart(2, '0')}/${date.getDate().toString().padStart(2, '0')}/${date.getFullYear().toString().substr(-2)}`;
  dateElement.textContent = formattedDate;
});
