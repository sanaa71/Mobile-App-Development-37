let tasks = JSON.parse(localStorage.getItem("tasks")) || [];

function addTask() {
  let taskInput = document.getElementById("taskInput");
  let deadlineInput = document.getElementById("deadlineInput");

  let taskText = taskInput.value.trim();
  let deadline = deadlineInput.value;

  if (taskText === "") {
    alert("Please enter a task");
    return;
  }

  let task = {
    text: taskText,
    deadline: deadline,
    completed: false
  };

  tasks.push(task);
  localStorage.setItem("tasks", JSON.stringify(tasks));

  taskInput.value = "";
  deadlineInput.value = "";

  displayTasks();
}

function displayTasks() {
  let list = document.getElementById("taskList");
  list.innerHTML = "";

  tasks.forEach((task, index) => {
    let li = document.createElement("li");

    if (task.completed) {
      li.classList.add("completed");
    }

    let taskInfo = document.createElement("div");
    taskInfo.classList.add("task-info");

    let title = document.createElement("span");
    title.textContent = task.text;

    let deadline = document.createElement("span");
    deadline.classList.add("deadline");
    deadline.textContent = task.deadline ? "Deadline: " + task.deadline : "";

    taskInfo.appendChild(title);
    taskInfo.appendChild(deadline);

    let actions = document.createElement("div");
    actions.classList.add("actions");

    // Complete button
    let completeBtn = document.createElement("button");
    completeBtn.textContent = "✔";
    completeBtn.onclick = () => toggleComplete(index);

    // Delete button
    let deleteBtn = document.createElement("button");
    deleteBtn.textContent = "🗑";
    deleteBtn.onclick = () => deleteTask(index);

    actions.appendChild(completeBtn);
    actions.appendChild(deleteBtn);

    li.appendChild(taskInfo);
    li.appendChild(actions);

    list.appendChild(li);
  });
}

function toggleComplete(index) {
  tasks[index].completed = !tasks[index].completed;
  localStorage.setItem("tasks", JSON.stringify(tasks));
  displayTasks();
}

function deleteTask(index) {
  tasks.splice(index, 1);
  localStorage.setItem("tasks", JSON.stringify(tasks));
  displayTasks();
}

displayTasks();