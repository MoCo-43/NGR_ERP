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
 	        m.innerText = message;
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