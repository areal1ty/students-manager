function getStudents() {
    const apiUrl = `http://localhost:8080/students`;

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP Error occurred ${response.status}`);
            }
            return response.json();
        }).then(data => {
        data.forEach(elem => {
            renderStudentCard(elem)
        });
    })
        .catch(error => {
            console.error('Error occurred', error);
        });
}

const students_list = document.querySelector('.students')

function renderStudentCard(student) {
    const element_0 = document.createElement('SECTION')
    element_0.className = 'card'
    const element_1 = document.createElement('H1')
    const element_2 = document.createTextNode(student.surname)
    element_1.appendChild(element_2)
    element_0.appendChild(element_1)
    const element_3 = document.createElement('DIV')
    const element_4 = document.createElement('P')
    const element_5 = document.createTextNode(`${student.name} ${student.patronymic}`)
    element_4.appendChild(element_5)
    element_3.appendChild(element_4)
    const element_6 = document.createElement('P')
    const element_7 = document.createTextNode(student.bd_date)
    element_6.appendChild(element_7)
    element_3.appendChild(element_6)
    element_0.appendChild(element_3)
    const element_8 = document.createElement('SPAN')
    const element_9 = document.createTextNode(student.study_group)
    element_8.appendChild(element_9)
    element_0.appendChild(element_8)
    const element_10 = document.createElement('SECTION')
    element_10.className = 'overlay'
    const element_11 = document.createElement('P')
    element_11.setAttribute('onclick', `deleteStudent(${student.id})`)
    const element_12 = document.createTextNode('Удалить')
    element_11.appendChild(element_12)
    element_10.appendChild(element_11)
    element_0.appendChild(element_10)

    students_list.appendChild(element_0)
}

let isShowModal = false

function modalHandler() {
    const modal = document.querySelector('.modal')
    if (isShowModal === false) {
        modal.classList.add('show')
    } else {
        modal.classList.remove('show')
    }
    isShowModal = !isShowModal
}

function deleteStudent(id) {
    fetch('http://localhost:8080/students', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(id),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP Error: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            clearList()
            getStudents()
        })
        .catch(error => {
            console.error('Error occurred:', error);
        });
}

function clearList() {
    students_list.innerHTML = ''
}

document.querySelector('form').addEventListener('submit', function (event) {
    event.preventDefault();

    let name = document.getElementById('name').value;
    let surname = document.getElementById('surname').value;
    let patronymic = document.getElementById('patronymic').value;
    let dateOfBirth = document.getElementById('date_of_birth').value;
    let group = document.getElementById('student_group').value;

    let data = {
        name: name,
        surname: surname,
        patronymic: patronymic,
        dateOfBirth: dateOfBirth,
        student_group: group
    };

    fetch('http://localhost:8080/students', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Cannot receive response');
            }
            return response.text();
        })
        .then(data => {
            clearList()
            getStudents()
            modalHandler()
        })
        .catch(error => {
            console.error('Error occurred:', error);
        });
});

function updateStudent(id, updatedData) {
    fetch(`http://localhost:8080/students/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP Error: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            clearList();
            getStudents();
        })
        .catch(error => {
            console.error('Error occurred:', error);
        });
}

window.onload = () => {
    getStudents()
}