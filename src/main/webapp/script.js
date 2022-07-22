// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
async function getMessages() {
    const responseFromServer = await fetch('/list-messages');
    const container = document.getElementById('thread-container');
    const entries = await responseFromServer.json()
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const groupID = localStorage.getItem('groupID');
    const threadID = urlParams.get('id')
    const userID = localStorage.getItem('userID');
    for (let i = 0; i < entries.length; i++) {
        const newMsg = document.createElement("div");
        if (entries[i].groupID === groupID) {
            if (entries[i].threadID === threadID) {
                const timestamp = entries[i].timestamp;
                const formattedTime = new Date(timestamp).toLocaleString();
                if (entries[i].userID === userID) {
                    newMsg.classList.add("thread-comment-self")
                }
                else {
                    newMsg.classList.add("thread-comment")
                }
                newMsg.textContent = entries[i].text;
                const nameContainer = document.createElement("div");
                nameContainer.classList.add("message-info-container")
                nameContainer.textContent = entries[i].userName + " at " + formattedTime;
                newMsg.appendChild(nameContainer)
                container.appendChild(newMsg);
            }
        }
    }
}

async function getThreads() {
    const responseFromServer = await fetch('/list-threads');
    const container = document.getElementById('all-threads-container');
    const threads = await responseFromServer.json()
    const groupID = localStorage.getItem('groupID');
    for (let i = 0; i < threads.length; i++) {
        if (threads[i].groupID === groupID) {
            const threadOuter = document.createElement('div');
            threadOuter.classList.add("forum-thread-outer");
            const newThread = document.createElement("a");
            threadOuter.appendChild(newThread);
            newThread.classList.add("forum-thread");
            newThread.textContent = threads[i].title;
            newThread.href=`/Thread.html?id=${threads[i].id}`;
            container.appendChild(threadOuter);
        }
    }
}

async function getGroups() {
    // For the selection bar on navbar
    const responseFromServer = await fetch('/list-groups');
    const container = document.getElementById('groups-list');
    const groups = await responseFromServer.json()
    const userID = localStorage.getItem('userID')
    for (let i = 0; i < groups.length; i++) {
        const groupID = groups[i].id;
        const isMembershipValid = await validateMembership(userID, groupID);
        if (isMembershipValid) {
            const group = document.createElement("option");
            group.text = groups[i].title;
            if (groups[i].id == localStorage.getItem("groupID")) {
                group.selected = "selected";
            }
            const groupIdField = document.createElement("input")
            groupIdField.type="hidden";
            groupIdField.id = "groupID"
            groupIdField.value = groups[i].id;
            group.appendChild(groupIdField);
            container.add(group);
        }
    }
}

async function displayGroups() {
    // For the groups page
    const responseFromServer = await fetch('/list-groups');
    const container = document.getElementById('all-groups-container');
    const groups = await responseFromServer.json()
    const userID = localStorage.getItem('userID')
    for (let i = 0; i < groups.length; i++) {
        const groupID = groups[i].id;
        const isMembershipValid = await validateMembership(userID, groupID);
        if (isMembershipValid) {
            const group = document.createElement("div");
            group.textContent = groups[i].title + " ";
            const idContainer = document.createElement("span");
            idContainer.textContent = groups[i].id;
            idContainer.classList.add("group-id-container");
            idContainer.style.display = "none";
            const showIdBtn = document.createElement("button")
            showIdBtn.classList.add("group-show-id-btn");
            showIdBtn.textContent = "Show ID"
            showIdBtn.onclick = function() { showIdBtn.style.display = "none"; idContainer.style.display = "block";};
            group.appendChild(idContainer);
            group.appendChild(showIdBtn);
            container.appendChild(group);
        }
    } 
    setGroup();
}


async function validateMembership(userID, groupID) {
    // Determines if a user is part of a group
    const responseFromServer = await fetch('/list-members');
    const members = await responseFromServer.json()
    for (let i = 0; i < members.length; i++) {
        if (members[i].userID == userID && members[i].groupID == groupID) {
            return true;
        }
    }
    return false
}

function showElement(id) {
    document.getElementById(id).style.display = "block";
}

function hideElement(id) {
    document.getElementById(id).style.display = "none";
}

function setGroupID(id) {
    localStorage.setItem('groupID', id);
}

function clearGroupID() {
    localStorage.removeItem('groupID');
}

function setGroup() {
    const sel = document.getElementById("groups-list");
    const selectedGroup = sel.options[sel.selectedIndex]
    const groupID = selectedGroup.getElementsByTagName('input')[0].value;
    localStorage.setItem('groupID', groupID);
    console.log("Group set to " + groupID)
}

function redirectToHome() {
    location.href = "home.html";
}

function loadTasks() {
    const groupID = localStorage.getItem('groupID');
    fetch('/list-tasks').then(response => response.json()).then((tasks) => {
      const taskListElement = document.getElementById('todo-list');
      tasks.forEach((task) => {
          if (task.groupID == groupID){
            taskListElement.appendChild(createTaskElement(task));
          }   
      })
    });
  }
  
  /** Creates an element that represents a task, including its delete button. */
  function createTaskElement(task) {
    const taskElement = document.createElement('li');
    taskElement.className = 'task';
  
    const titleElement = document.createElement('span');
    titleElement.innerText = task.title;
  
    const deleteButtonElement = document.createElement('button');
    deleteButtonElement.innerText = "Completed";
    deleteButtonElement.addEventListener('click', () => {
      deleteTask(task);
  
      // Remove the task from the DOM.
      taskElement.remove();
    });
  
    taskElement.appendChild(deleteButtonElement);
    taskElement.appendChild(titleElement);

    return taskElement;
  }
  
  /** Tells the server to delete the task. */
  function deleteTask(task) {
    const params = new URLSearchParams();
    params.append('id', task.id);
    fetch('/delete-task', {method: 'POST', body: params});
  }
  