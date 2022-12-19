import { Axios, AxiosInstance } from "axios"
import { Form } from "../types"
import { Response, ResponseApprovePreApprovalStatus } from "../types/responseTypes"

export const getPreApprovalStatus = async (axios: AxiosInstance, bilkentId: string) => {
    const response = await axios.get<ResponseApprovePreApprovalStatus>(`/fileService/form/student/${bilkentId}/preApproval/status/`)
    return response.data
}

// formData should contain the File object in the "file" property and FileType in the "fileType" property
export const submitFile = async (axios: AxiosInstance, formData: FormData, studentId: string) => {
    const response = await axios.post(`/fileService/form/${studentId}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
    return response.data
}

export const generateAndDownloadPreApproval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.post(`/fileService/form/generate/download/student/${studentId}/${Form.PRE_APPROVAL}`, {
        responseType: "blob",
        headers: {
            'Accept': 'application/pdf'
        }
    })
    return response.data
}

export const generateAndSubmitPreApproval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.post(`/fileService/form/generate/submit/student/${studentId}/${Form.PRE_APPROVAL}`)
    return response.data
}

export const submitSignature = async (axios: AxiosInstance, formData: FormData, userId: string) => {
    const response = await axios.post(`/signature/user/${userId}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
    return response.data
}

export const downloadSignature = async (axios: AxiosInstance, userId: string) => {
    const response = await axios.get(`/signature/user/${userId}`, {
        responseType: "blob",
    })
    return response.data
}

export const downloadForm = async (axios: AxiosInstance, userId: string, formType: Form) => {
    const response = await axios.get(`fileService/form/${userId}/${formType}`, {
        responseType: "blob",
    })
    return response.data
}

export const deleteForm = async (axios: AxiosInstance, userId: string, formType: Form) => {
    const response = await axios.delete(`fileService/form/${userId}/${formType}`)
    return response.data
}