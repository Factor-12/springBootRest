getAllUsers()

function getAllUsers () {
  $('#table').empty()
  $.getJSON('/api/admin', function (data) {
    $.each(data, function (key, user) {
      let roles = []
      for (let i = 0; i < user.roles.length; i++) {
        roles.push(user.roles[i].name.substring(5))
      }
      $('#tableAllUsers').append($('<tr>').append(
          $('<td>').text(user.id),
          $('<td>').text(user.username),
          $('<td>').text(user.lastname),
          $('<td>').text(user.age),
          $('<td>').text(user.email),
          $('<td>').text(roles.join(', ')),
          $('<td>').
          append(
              '<button type=\'button\' data-toggle=\'modal\' class=\'btn-info btn\'' +
              ' data-target=\'#editUserModal\' data-user-id=\'' + user.id +
              '\'>Edit</button>'),
          $('<td>').
          append(
              '<button type=\'button\' data-toggle=\'modal\' class=\'btn btn-danger\'' +
              ' data-target=\'#deleteUserModal\' data-user-id=\'' + user.id +
              '\'>Delete</button>'),
          ),
      )
    })
  })
  $('[href="#admin"]').on('show.bs.tab', (e) => {
    location.reload()
  })
}



$('[href="#userss"]').on('show.bs.tab', (e) => {
  $('#change-tabContent').hide(),
      getCurrent()
})

function getCurrent () {
  $.ajax({
    url: '/getUser',
    type: 'GET',
    dataType: 'json',
  }).done((msg) => {
    let user = JSON.parse(JSON.stringify(msg))
    let roles = []
    for (let i = 0; i < user.roles.length; i++) {
      roles.push(user.roles[i].name.substring(5))
    }
    $('#current-user-table tbody').empty().append(
        $('<tr>').append(
            $('<td>').text(user.id),
            $('<td>').text(user.username),
            $('<td>').text(user.lastname),
            $('<td>').text(user.age),
            $('<td>').text(user.email),
            $('<td>').text(roles.join(', ')),
        ))
  }).fail(() => {
    alert('Error Get All Users!')
  })
}
//Edit
$('#editUserModal').on('show.bs.modal', (e) => {
  let userId = $(e.relatedTarget).data('user-id')

  $.ajax({
    url: '/api/admin/' + userId,
    type: 'GET',
    dataType: 'json',
  }).done((msg) => {
    let user = JSON.parse(JSON.stringify(msg))
    $.getJSON('api/admin/roles', function (editRole) {
      $('#id').empty().val(user.id)
      $('#editName').empty().val(user.username)
      $('#editLastName').empty().val(user.lastname)
      $('#editAge').empty().val(user.age)
      $('#editEmail').empty().val(user.email)
      $('#editPassword').empty().val(user.password)
      $('#editRoles').empty()
      $.each(editRole, (i, role) => {
        $('#editRoles').append(
            $('<option>').text(role.name.substring(5)),
        )
      })
      $('#editRoles').val(user.roles)
    })
  })
})

$('#buttonEditSubmit').on('click', (e) => {
  e.preventDefault()

  let editUser = {
    id: $('#id').val(),
    username: $('#editName').val(),
    lastname: $('#editLastName').val(),
    age: $('#editAge').val(),
    email: $('#editEmail').val(),
    password: $('#editPassword').val(),
    roles: $('#editRoles').val(),
  }

  $.ajax({
    url: '/api/admin',
    type: 'PUT',
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    data: JSON.stringify(editUser),
  })
    $('#editUserModal').modal('hide')
    location.reload()
    getAllUsers()
})
//Delete
$('#deleteUserModal').on('show.bs.modal', (e) => {
  let userId = $(e.relatedTarget).data('user-id')

  $.ajax({
    url: '/api/admin/' + userId,
    type: 'GET',
    dataType: 'json',
  }).done((msg) => {
    let user = JSON.parse(JSON.stringify(msg))

    $('#delId').empty().val(user.id)
    $('#delName').empty().val(user.username)
    $('#delLastName').empty().val(user.lastname)
    $('#delAge').empty().val(user.age)
    $('#delEmail').empty().val(user.email)

    $('#buttonDel').on('click', (e) => {
      e.preventDefault()

      $.ajax({
        url: '/api/admin/' + userId,
        type: 'DELETE',
        dataType: 'text',
      }).done((deleteMsg) => {
        $(`#deleteUserModal`).modal('hide')
        location.reload()
      })
    })
  })
})

$('[href="#new"]').on('show.bs.tab', (e) => {
  $.getJSON('api/admin/roles', function (newUser) {
    $(() => {
      $('#name').empty().val('')
      $('#lastName').empty().val('')
      $('#age').empty().val('')
      $('#email').empty().val('')
      $('#password').empty().val('')
      $('#rolesNew').empty().val('')
      $.each(newUser, function (k, role) {
        $('#rolesNew').append(
            $('<option>').text(role.name.substring(5)),
        )
      })
    })
  })
})
$('#buttonNew').on('click', (e) => {
  e.preventDefault()

  let newUser = {
    username: $('#name').val(),
    lastname: $('#lastName').val(),
    age: $('#age').val(),
    email: $('#email').val(),
    password: $('#password').val(),
    roles: $('#rolesNew').val(),
  }
  let json = JSON.stringify(newUser);
  console.log(json);

  $.ajax({
    url: '/api/admin',
    type: 'POST',
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    data: json,
  })
  getAllUsers(),
      $('#testTab a[href="#usersTable"]').tab('show')
  location.reload()
})