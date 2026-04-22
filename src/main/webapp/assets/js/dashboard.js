// Dashboard specific JavaScript

document.addEventListener('DOMContentLoaded', function() {
    const quickActionButtons = document.querySelectorAll('.quick-action-btn, .hero-primary-action, .hero-secondary-action');

    quickActionButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const action = this.textContent.trim();
            const originalContent = this.innerHTML;
            this.classList.add('is-loading');
            TobIAs.showLoading(this);

            setTimeout(() => {
                TobIAs.hideLoading(this, originalContent);
                this.classList.remove('is-loading');
                TobIAs.showToast(`${action} - Funcionalidade em desenvolvimento`, 'info');
            }, 1000);
        });
    });

    const statsCards = document.querySelectorAll('.card');
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    statsCards.forEach(card => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        card.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
        observer.observe(card);
    });
});
