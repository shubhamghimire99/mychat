var main = document.getElementsByClassName("main")[0];
axios.get("http://localhost:8585/users").then((res) => {
  res.data.forEach((element) => {
    main.innerHTML += `<form class="form" action="/addFriend" method="post">
        <input hidden type="text" name="id" value="${element.id}">
        <h2>${element.name}</h2>