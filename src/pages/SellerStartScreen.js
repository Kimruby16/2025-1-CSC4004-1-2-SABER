// SellerStartScreen.js
import { useEffect, useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../css/Sellers.css';

export default function SellerStartScreen() {
    const [searchParams] = useSearchParams();
    const [token, setToken] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    function getVisitorKey() {
        let key = localStorage.getItem('visitorKey');
        if (!key) {
            key = crypto.randomUUID();
            localStorage.setItem('visitorKey', key);
        }
        return key;
    }

    const startVerification = async (tokenToUse) => {
        if (!tokenToUse) {
            setError('토큰이 없습니다');
            return;
        }
        setError('');
        setLoading(true);
        try {
            const visitorKey = getVisitorKey();
            const res = await axios.get(`http://localhost:8080/api/saber`, {
                params: {
                    token: tokenToUse,
                    visitorKey: visitorKey,
                },
                withCredentials: true,
            });

            localStorage.setItem('sessionId', res.data.id);
            navigate('/seller/permission');
        } catch (e) {
            setError('인증 시작 실패');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const tokenFromUrl = searchParams.get('token');
        if (tokenFromUrl) {
            setToken(tokenFromUrl);
        }
    }, [searchParams]);

    return (
        <div className="container">
            <h2 className="title">판매자 인증을 시작합니다</h2>
            <p className="description">아래 버튼을 눌러 본인 인증을 시작해 주세요</p>

            <button
                className="doneButton"
                onClick={() => startVerification(token)}
                disabled={loading || !token}
            >
                인증 시작
            </button>

            {error && <p className="timerText" style={{ color: 'red' }}>{error}</p>}
        </div>
    );
}
