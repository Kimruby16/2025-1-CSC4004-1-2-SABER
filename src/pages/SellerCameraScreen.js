// SellerCameraScreen.js
import React, { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../css/Sellers.css';

function SellerCameraScreen() {
  const videoRef = useRef(null);
  const canvasRef = useRef(null);
  const navigate = useNavigate();
  const [stream, setStream] = useState(null);

  // 1. 카메라 접근
  useEffect(() => {
    const startCamera = async () => {
      try {
        const mediaStream = await navigator.mediaDevices.getUserMedia({ video: true });
        if (videoRef.current) {
          videoRef.current.srcObject = mediaStream;
        }
        setStream(mediaStream);
      } catch (err) {
        console.error('카메라 접근 오류:', err);
      }
    };

    startCamera();

    return () => {
      if (stream) {
        stream.getTracks().forEach((track) => track.stop());
      }
    };
  }, [stream]);

  // 2. 보안 위험 감지
  useEffect(() => {
    const handleSecurityRisk = () => {
      if (stream) {
        stream.getTracks().forEach((track) => track.stop());
        setStream(null);
      }
      navigate('/capture-warning');
    };

    const handleVisibilityChange = () => {
      if (document.visibilityState === 'hidden') {
        handleSecurityRisk();
      }
    };

    const handleBlur = () => {
      handleSecurityRisk();
    };

    window.addEventListener('blur', handleBlur);
    document.addEventListener('visibilitychange', handleVisibilityChange);

    return () => {
      window.removeEventListener('blur', handleBlur);
      document.removeEventListener('visibilitychange', handleVisibilityChange);
    };
  }, [navigate, stream]);

  // 3. 촬영 및 업로드
  const handleCapture = async () => {
    const video = videoRef.current;
    const canvas = canvasRef.current;
    if (video && canvas) {
      const context = canvas.getContext('2d');
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      context.drawImage(video, 0, 0, canvas.width, canvas.height);

      canvas.toBlob(async (blob) => {
        if (!blob) return;

        const formData = new FormData();
        formData.append('file', blob, 'capture.png');

        try {
          const res = await axios.post('http://localhost:8080/api/upload', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
          console.log('업로드 성공:', res.data);
        } catch (err) {
          console.error('업로드 실패:', err);
          return;
        }

        if (stream) {
          stream.getTracks().forEach((track) => track.stop());
          setStream(null);
        }

        navigate('/seller/verification-complete');
      }, 'image/png');
    }
  };

  return (
    <div className="captureContainer">
      {!stream && <div className="cameraLoading">카메라 로딩 중... 또는 카메라 접근을 허용해주세요.</div>}
      <video ref={videoRef} autoPlay playsInline className="video" />
      <canvas ref={canvasRef} style={{ display: 'none' }} />
      <button onClick={handleCapture} className="captureButton" disabled={!stream} />
      <div className="cameraLabel">사진 촬영</div>
    </div>
  );
}

export default SellerCameraScreen;