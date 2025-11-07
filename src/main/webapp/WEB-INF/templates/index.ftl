<#assign pageTitle = "Home English School" />
<#assign extraScripts = ["index.js"] />
<#include "fragments/header.ftl" />

<section id="top" class="hero">
    <div class="wrap center">
        <h1>Изучайте английский легко и с удовольствием!</h1>
        <p>Курсы для начинающих, продвинутых и даже для пилотов ✈️</p>
        <a href="#courses" class="bigbtn">Выбрать курс</a>
    </div>
</section>

<section id="courses" class="section">
    <div class="wrap center">
        <h2>Выберите курс</h2>
        <p>Нажмите на курс, чтобы отправить заявку</p>

        <div class="course-grid">
            <div class="course-card" data-course="conversational-basic">
                <div class="emoji">🗣️</div>
                <h3>Разговорный английский — Базовый</h3>
                <p>Подходит тем, кто только начинает изучение языка.</p>
            </div>

            <div class="course-card" data-course="conversational-advanced">
                <div class="emoji">💬</div>
                <h3>Разговорный английский — Продвинутый</h3>
                <p>Для тех, кто хочет уверенно говорить на любые темы.</p>
            </div>

            <div class="course-card" data-course="aviation-pilots">
                <div class="emoji">✈️</div>
                <h3>Авиационный английский для пилотов</h3>
                <p>Подготовка к международным стандартам ICAO.</p>
            </div>

            <div class="course-card" data-course="aviation-dispatchers">
                <div class="emoji">🛫</div>
                <h3>Английский для диспетчеров</h3>
                <p>Коммуникация и терминология управления полетами.</p>
            </div>
        </div>
    </div>
</section>

<div id="applicationModal" class="modal hidden">
    <div class="modal-inner">
        <button type="button" class="close" aria-label="Закрыть форму">&times;</button>
        <h3 id="modalTitle">Оставить заявку</h3>

        <form id="applicationForm" method="post" action="submitApplication">
            <input type="hidden" id="selectedCourseId" name="courseType">

            <div class="frow">
                <label for="userName">Ваше имя *</label>
                <input type="text" id="userName" name="studentName" required
                       value="${(userName!)?default('')}">
            </div>

            <div class="frow">
                <label for="userEmail">Email *</label>
                <input type="email" id="userEmail" name="email" required
                       value="${(userEmail!)?default('')}">
            </div>

            <div class="frow">
                <label for="userPhone">Телефон *</label>
                <input type="tel" id="userPhone" name="phone" required pattern="^\+?[0-9]{7,15}$"
                       title="Введите телефон в формате +71234567890">
            </div>

            <div class="frow">
                <label for="userMessage">Ваши цели и пожелания</label>
                <textarea id="userMessage" name="message" rows="4"></textarea>
            </div>

            <button type="submit" class="sendbtn">Отправить заявку</button>
        </form>
    </div>
</div>

<section id="advantages" class="section alt">
    <div class="wrap">
        <h2>Почему выбирают нас</h2>
        <div class="cards">
            <div class="card"><span>🎯</span><h4>Индивидуальный подход</h4><p>Каждый студент получает персональную программу и внимание преподавателя.</p></div>
            <div class="card"><span>🌍</span><h4>Опытные преподаватели</h4><p>Наши учителя — сертифицированные специалисты с международным опытом.</p></div>
            <div class="card"><span>💬</span><h4>Разговорная практика</h4><p>Каждое занятие направлено на развитие уверенного общения.</p></div>
            <div class="card"><span>✈️</span><h4>Спецкурсы для авиации</h4><p>Профессиональный английский для пилотов и авиадиспетчеров.</p></div>
        </div>
    </div>
</section>

<section id="process" class="section">
    <div class="wrap">
        <h2>Как проходит обучение</h2>
        <div class="steps">
            <div class="step">
                <span class="num">1</span>
                <h4>Определяем ваш уровень</h4>
                <p>Вы проходите короткое онлайн-тестирование, и мы подбираем курс под ваш текущий уровень знаний.</p>
            </div>

            <div class="step">
                <span class="num">2</span>
                <h4>Составляем план занятий</h4>
                <p>Мы определяем цели обучения: разговорная практика, подготовка к экзамену или профессиональный английский.</p>
            </div>

            <div class="step">
                <span class="num">3</span>
                <h4>Регулярные онлайн-уроки</h4>
                <p>Вы занимаетесь с преподавателем в удобное время через Zoom или другую платформу, с интерактивными материалами.</p>
            </div>

            <div class="step">
                <span class="num">4</span>
                <h4>Контроль прогресса</h4>
                <p>Мы отслеживаем ваш рост, проводим мини-тесты и даем обратную связь после каждого этапа.</p>
            </div>

            <div class="step">
                <span class="num">5</span>
                <h4>Реальные результаты</h4>
                <p>Через несколько месяцев вы уверенно общаетесь, смотрите фильмы без перевода и используете английский в работе.</p>
            </div>
        </div>
    </div>
</section>

<#include "fragments/footer.ftl" />

