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
    const container = document.getElementById('messages-container');
    const entries = await responseFromServer.json()
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const threadID = urlParams.get('id')
    console.log(entries)
    for (let i = 0; i < entries.length; i++) {
        const newMsg = document.createElement("div");
        if (entries[i].threadID === threadID) {
            newMsg.classList.add("message")
            newMsg.textContent = entries[i].text;
            container.appendChild(newMsg);
        }
    }
}
