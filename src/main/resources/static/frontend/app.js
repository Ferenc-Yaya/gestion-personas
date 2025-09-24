// app.js - Funciones compartidas para el módulo de personas

const API_URL = 'http://localhost:8080/api/v1';

// Funciones de utilidad para fechas
function formatDate(dateString) {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString('es-ES');
}

function formatDateForInput(dateString) {
    if (!dateString) return '';
    return new Date(dateString).toISOString().split('T')[0];
}

function getCurrentDate() {
    return new Date().toISOString().split('T')[0];
}

// Validaciones
function validarDNI(dni) {
    if (!dni) return false;
    return /^\d{8}$/.test(dni);
}

function validarTelefono(telefono) {
    if (!telefono) return true;
    return /^[+]?[0-9]{9,15}$/.test(telefono);
}

function validarEmail(email) {
    if (!email) return true;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validarScore(score) {
    if (!score) return true;
    const numScore = parseInt(score);
    return numScore >= 0 && numScore <= 100;
}

// Funciones de mensajes
function mostrarMensaje(mensaje, tipo = 'info') {
    const existingMessages = document.querySelectorAll('.mensaje-toast');
    existingMessages.forEach(msg => msg.remove());

    const messageDiv = document.createElement('div');
    messageDiv.className = `mensaje-toast mensaje-${tipo}`;
    messageDiv.textContent = mensaje;

    messageDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        border-radius: 5px;
        color: white;
        font-weight: bold;
        z-index: 1000;
        max-width: 400px;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    `;

    switch (tipo) {
        case 'success':
            messageDiv.style.backgroundColor = '#28a745';
            break;
        case 'error':
            messageDiv.style.backgroundColor = '#dc3545';
            break;
        case 'warning':
            messageDiv.style.backgroundColor = '#ffc107';
            messageDiv.style.color = '#000';
            break;
        default:
            messageDiv.style.backgroundColor = '#17a2b8';
    }

    document.body.appendChild(messageDiv);

    setTimeout(() => {
        if (messageDiv.parentNode) {
            messageDiv.parentNode.removeChild(messageDiv);
        }
    }, 5000);
}

function confirmar(mensaje) {
    return confirm(mensaje);
}

function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Constantes útiles
const MENSAJES = {
    PERSONA_CREADA: 'Persona creada exitosamente',
    PERSONA_ACTUALIZADA: 'Persona actualizada exitosamente',
    PERSONA_ELIMINADA: 'Persona eliminada exitosamente',
    ERROR_VALIDACION: 'Por favor, complete todos los campos requeridos',
    CONFIRMAR_ELIMINAR: '¿Está seguro de que desea eliminar este registro?'
};