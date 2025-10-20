// ===== cuteAlert 전역 스타일 자동 삽입 =====
if (!document.getElementById("cuteAlertStyles")) {
  const style = document.createElement("style");
  style.id = "cuteAlertStyles";
  style.textContent = `
  .cute-alert-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0,0,0,0.35);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity .25s ease;
    z-index: 9999;
  }
  .cute-alert-overlay.active { opacity: 1; }

  .cute-alert-box {
    background: #fff;
    padding: 1.25rem 1.5rem;
    border-radius: 10px;
    width: 300px;
    max-width: 90%;
    text-align: center;
    box-shadow: 0 5px 15px rgba(0,0,0,0.25);
    transform: translateY(-20px);
    transition: transform .3s ease;
  }
  .cute-alert-overlay.active .cute-alert-box { transform: translateY(0); }

  .cute-alert-icon {
    font-size: 2.2rem;
    margin-bottom: .5rem;
  }
  .cute-alert-title {
    font-size: 1.1rem;
    font-weight: 700;
    margin-bottom: .4rem;
    color: black;
  }
  .cute-alert-message {
    font-size: .9rem;
    margin-bottom: 1rem;
    color: #333;
    line-height: 1.4;
  }
  .cute-alert-buttons {
    display: flex;
    justify-content: center;
    gap: .5rem;
  }
  .confirm-btn, .cancel-btn {
    min-width: 70px;
    padding: .35rem .7rem;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 600;
  }
  .confirm-btn { background: #2563eb; color: white; }
  .cancel-btn { background: #e5e7eb; }
  `;
  document.head.appendChild(style);
}

function cuteAlert({ type='question', title='알림', message='', imgUrl=null, confirmText='확인', cancelText='취소' }) {
 	    return new Promise((resolve) => {
 	        // 모달 오버레이 생성
 	        const overlay = document.createElement('div');
 	        overlay.className = 'cute-alert-overlay';
 	        document.body.appendChild(overlay);

 	        // 모달 박스 생성
 	        const box = document.createElement('div');	
 	        box.className = 'cute-alert-box';

 	        // 아이콘 이미지
 	        if (imgUrl) {
 	            const icon = document.createElement('img');
 	            icon.src = imgUrl;
 	            icon.className = 'cute-alert-icon';
 	            box.appendChild(icon);
 	        } else {
 	            const icon = document.createElement('div');
 	            icon.className = 'cute-alert-icon';
 	            icon.innerHTML = type === 'question' ? '❓' :
 	                             type === 'success' ? '✅' :
 	                             type === 'warning' ? '⚠️' :
 	                             type === 'error' ? '❌' : '';
 	            box.appendChild(icon);
 	        }

 	        // 제목
 	        const t = document.createElement('div');
 	        t.className = 'cute-alert-title';
 	        t.innerText = title;
 	        box.appendChild(t);

 	        // 메시지
 	        const m = document.createElement('div');
 	        m.className = 'cute-alert-message';
 	        m.innerHTML = message.replace(/\n/g, '<br>');
 	        box.appendChild(m);

 	        // 버튼
 	        const btnContainer = document.createElement('div');
 	        btnContainer.className = 'cute-alert-buttons';

 	        const confirmBtn = document.createElement('button');
 	        confirmBtn.className = 'confirm-btn';
 	        confirmBtn.innerText = confirmText;

 	        const cancelBtn = document.createElement('button');
 	        cancelBtn.className = 'cancel-btn';
 	        cancelBtn.innerText = cancelText;

 	        btnContainer.appendChild(confirmBtn);
 	        btnContainer.appendChild(cancelBtn);
 	        box.appendChild(btnContainer);

 	        overlay.appendChild(box);

 	        // 모달 표시
 	        requestAnimationFrame(() => overlay.classList.add('active'));

 	        // 버튼 클릭 이벤트
 	        confirmBtn.addEventListener('click', () => {
 	            overlay.classList.remove('active');
 	            setTimeout(() => {
 	                document.body.removeChild(overlay);
 	                resolve(true);
 	            }, 300);
 	        });
 	        cancelBtn.addEventListener('click', () => {
 	            overlay.classList.remove('active');
 	            setTimeout(() => {
 	                document.body.removeChild(overlay);
 	                resolve(false);
 	            }, 300);
 	        });
 	    });
 	}