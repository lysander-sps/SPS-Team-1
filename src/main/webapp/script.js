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
    for (let i = 0; i < entries.length; i++) {
        const newMsg = document.createElement("div");
        if (entries[i].groupID === groupID) {
            if (entries[i].threadID === threadID) {
                newMsg.classList.add("thread-comment")
                newMsg.textContent = entries[i].text;
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
    const responseFromServer = await fetch('/list-groups');
    const container = document.getElementById('groups-list');
    const groups = await responseFromServer.json()
    for (let i = 0; i < groups.length; i++) {
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

function setGroup(sel) {
    const selectedGroup = sel.options[sel.selectedIndex]
    const groupID = selectedGroup.getElementsByTagName('input')[0].value;
    localStorage.setItem('groupID', groupID);
    console.log("Group set to " + groupID)
}

function redirectToHome() {
    location.href = "home.html";
}