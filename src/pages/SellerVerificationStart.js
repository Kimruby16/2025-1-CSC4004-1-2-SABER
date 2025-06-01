// SellerVerificationStart.js
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../css/Sellers.css"; // ✅ 판매자 전용 스타일 불러오기
import logoImage from "../assets/logo.png";

function SellerVerificationStart() {
    const [pendingIds, setPendingIds] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();
    const VERIFICATION_TIMEOUT = 180; // 제한 시간 (초)

    useEffect(() => {
        const verificationLinkId = localStorage.getItem("sessionId");

        if (!verificationLinkId) {
            console.error("로컬스토리지에 verificationLinkId가 없습니다.");
            return;
        }

        axios
            .get(`http://localhost:8080/api/saber/link/${verificationLinkId}/pending-verification-ids`)
            .then((res) => {
                setPendingIds(res.data);
            })
            .catch((err) => {
                console.error("ID 불러오기 실패", err);
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []);

    useEffect(() => {
        if (!isLoading && pendingIds.length === 0) {
            const timeoutId = setTimeout(() => {
                navigate('/seller/submit');
            }, 5000);
            return () => clearTimeout(timeoutId);
        }
    }, [pendingIds, isLoading, navigate]);

    const startVerification = (id) => {
        axios
            .post(`http://localhost:8080/api/verification/${id}/start`)
            .then(() => {
                navigate(`/verifications/${id}/camera`);
            })
            .catch((err) => {
                console.error("인증 시작 실패", err);
            });
    };

    const goToHome = () => {
        navigate('/');
    };

    return (
        <div className="container">
            <div className="header">
                <div className="logo-with-text" onClick={goToHome}>
                    <img src={logoImage} alt="SABER Logo" className="logo-image" />
                    <div className="logo-text">SABER</div>
                </div>
                <div className="menuIcon">☰</div>
            </div>

            <div className="progressBarContainer">
                <div className="progressBar" style={{ width: '10%' }}></div>
            </div>

            <h1 className="title">판매자 인증 항목</h1>
            <p className="description">
                아래 항목들에 대해 실물 인증을 진행해야 합니다.
                <br />
                각 항목마다 인증 시작 시 <strong>{VERIFICATION_TIMEOUT}초</strong>의 제한 시간이 부여됩니다.
            </p>

            {isLoading && <p className="message">인증 요청을 불러오는 중입니다...</p>}

            {!isLoading && pendingIds.length === 0 && (
                <p className="message">모든 인증이 완료되었습니다. 5초 후 제출 페이지로 이동합니다.</p>
            )}

            <div className="buttonGroup">
                {pendingIds.map((id) => (
                    <button
                        key={id}
                        className="doneButton"
                        onClick={() => startVerification(id)}
                    >
                        인증 시작 (ID: {id})
                    </button>
                ))}
            </div>
        </div>
    );
}

export default SellerVerificationStart;