let currentStudentId = null;

function showModal(student) {
    const modal = document.querySelector('.modal');
    const form = document.getElementById('studentForm');

    if (student) {
        currentStudentId = student.id;
        document.getElementById('modalTitle').textContent = 'Изменить студента';
        document.getElementById('lastName').value = student.lastName;
        document.getElementById('firstName').value = student.firstName;
        document.getElementById('patronymic').value = student.patronymic;
        document.getElementById('studentGroup').value = student.studentGroup;
        document.getElementById('dateOfBirth').value = student.dateOfBirth;
    } else {
        currentStudentId = null;
        document.getElementById('modalTitle').textContent = 'Добавить студента';
        form.reset();
    }

    modal.classList.add('show');
}

function closeModal() {
    const modal = document.querySelector('.modal');
    modal.classList.remove('show');
}

function getStudents() {
    fetch('http://localhost:8081/students')
        .then(response => response.json())
        .then(data => {
            const studentsList = document.querySelector('.students');
            studentsList.innerHTML = '';
            data.forEach(student => renderStudentCard(student));
        })
        .catch(error => console.error('Error:', error));
}

function renderStudentCard(student) {
    const card = document.createElement('div');
    card.className = 'card';
    card.setAttribute('data-id', student.id);

    const name = document.createElement('h1');
    name.textContent = student.lastName;
    card.appendChild(name);

    const details = document.createElement('div');
    const fullName = document.createElement('p');
    fullName.textContent = `${student.firstName} ${student.patronymic}`;
    details.appendChild(fullName);

    const dob = document.createElement('p');
    dob.textContent = student.dateOfBirth;
    details.appendChild(dob);

    card.appendChild(details);

    const studentGroup = document.createElement('span');
    studentGroup.textContent = student.studentGroup;
    card.appendChild(studentGroup);

    const actionButtons = document.createElement('div');
    actionButtons.className = 'action-buttons';

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Удалить';
    deleteButton.onclick = () => deleteStudent(student.id);
    actionButtons.appendChild(deleteButton);

    const editButton = document.createElement('button');
    editButton.textContent = 'Изменить';
    editButton.className = 'edit';
    editButton.onclick = () => showModal(student);
    actionButtons.appendChild(editButton);

    card.appendChild(actionButtons);

    const studentsList = document.querySelector('.students');
    studentsList.appendChild(card);
}

function updateOrCreateStudentCard(student) {
    const existingCard = document.querySelector(`.card[data-id='${student.id}']`);
    if (existingCard) {
        existingCard.querySelector('h1').textContent = student.lastName;
        existingCard.querySelector('p').textContent = `${student.firstName} ${student.patronymic}`;
        existingCard.querySelector('span').textContent = student.studentGroup;
        existingCard.querySelector('div > p:last-of-type').textContent = student.dateOfBirth;
    } else {
        renderStudentCard(student);
    }
}

function deleteStudent(id) {
    fetch(`http://localhost:8081/students/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(() => {
            const studentCard = document.querySelector(`.card[data-id='${id}']`);
            if (studentCard) {
                studentCard.remove();
            }
        })
        .catch(error => console.error('Error:', error));
}

document.getElementById('studentForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const studentData = {
        id: currentStudentId, // добавляем ID для обновления
        lastName: document.getElementById('lastName').value,
        firstName: document.getElementById('firstName').value,
        patronymic: document.getElementById('patronymic').value,
        dateOfBirth: document.getElementById('dateOfBirth').value,
        studentGroup: document.getElementById('studentGroup').value
    };

    let url = 'http://localhost:8081/students';
    let method = 'POST';

    if (currentStudentId) {
        method = 'PUT';
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(studentData),
    })
        .then(response => response.json())
        .then(student => {
            closeModal();
            updateOrCreateStudentCard(student);
        })
        .catch(error => console.error('Error:', error));
});

window.onload = getStudents;
