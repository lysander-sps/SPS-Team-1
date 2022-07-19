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
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!', '안녕하세요!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/** Fetches the current date from the server and adds it to the page. */
async function showServerTime() {
    const responseFromServer = await fetch('/date');
    const textFromResponse = await responseFromServer.text();
  
    const dateContainer = document.getElementById('date-container');
    dateContainer.innerText = textFromResponse;
  }

jQuery(document).ready(function( $ ) {

    // Smooth scroll for the menu and links with .scrollto classes
    $('.smothscroll').on('click', function(e) {
      e.preventDefault();
      if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
        var target = $(this.hash);
        if (target.length) {
  
          $('html, body').animate({
            scrollTop: target.offset().top - 62
          }, 1500, 'easeInOutExpo');
        }
      }
    });
  
    $('.carousel').carousel({
      interval: 3500
    });
  
    // Java Chart
    var doughnutData = [{
        value: 70,
        color: "#1abc9c"
      },
      {
        value: 30,
        color: "#ecf0f1"
      }
    ];
    var myDoughnut = new Chart(document.getElementById("java").getContext("2d")).Doughnut(doughnutData);
  
    // Python Chart
    var doughnutData = [{
      value: 90,
      color: "#1abc9c"
    },
    {
      value: 10,
      color: "#ecf0f1"
    }
    ];
    var myDoughnut = new Chart(document.getElementById("python").getContext("2d")).Doughnut(doughnutData);
  
    // C++ Chart
    var doughnutData = [{
      value: 65,
      color: "#1abc9c"
    },
    {
      value: 35,
      color: "#ecf0f1"
    }
    ];
    var myDoughnut = new Chart(document.getElementById("c++").getContext("2d")).Doughnut(doughnutData);
  
    // HTML Chart
    var doughnutData = [{
      value: 80,
      color: "#1abc9c"
    },
    {
      value: 20,
      color: "#ecf0f1"
    }
    ];
    var myDoughnut = new Chart(document.getElementById("html").getContext("2d")).Doughnut(doughnutData);
  
    // CSS Chart
    var doughnutData = [{
      value: 70,
      color: "#1abc9c"
    },
    {
      value: 30,
      color: "#ecf0f1"
    }
    ];
    var myDoughnut = new Chart(document.getElementById("css").getContext("2d")).Doughnut(doughnutData);
  
    // Javascript Chart
    var doughnutData = [{
      value: 50,
      color: "#1abc9c"
    },
    {
      value: 50,
      color: "#ecf0f1"
    }
    ];
    var myDoughnut = new Chart(document.getElementById("javascript").getContext("2d")).Doughnut(doughnutData);
  });