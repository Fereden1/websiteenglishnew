<#assign _extraScripts = [] />
<#if extraScripts??>
    <#if extraScripts?is_sequence>
        <#assign _extraScripts = extraScripts />
    <#else>
        <#assign _extraScripts = [extraScripts] />
    </#if>
</#if>

<footer class="foot">
    <div class="wrap center">
        <p>© 2025 Home English School</p>
    </div>
</footer>

<script src="main.js" defer></script>
<#list _extraScripts as scriptPath>
    <script src="${scriptPath}" defer></script>
</#list>

</body>
</html>

